package com.lsz.mall.manage.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsz.mall.base.entity.*;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.manage.dao.AdminDao;
import com.lsz.mall.manage.dao.AdminRoleRelationDao;
import com.lsz.mall.manage.dao.AuthDao;
import com.lsz.mall.manage.entity.AdminUserDetails;
import com.lsz.mall.manage.service.AdminService;
import com.lsz.mall.manage.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminDao adminDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthDao authDao;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    AdminRoleRelationDao adminRoleRelationDao;

    @Autowired
    RedissonClient redisson;

    @Override
    public Admin register(AdminParam adminParam) {
        Admin admin = new Admin();
        BeanUtils.copyProperties(adminParam, admin);

        admin.setCreateTime(new Date());
        admin.setStatus(1);

        // 查询同名用户
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AdminParam::getUsername, admin.getUsername());
        List<Admin> admins = adminDao.selectList(queryWrapper);
        if (!admins.isEmpty()) {
            return null;
        }

        // 将密码进行加密操作
        // 加盐为什么能判断match：https://www.zhihu.com/question/372852988/answer/1060384264

        String encodePassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encodePassword);
        adminDao.insert(admin);

        RBucket<Admin> adminRBucket = getMemberBucketByUserName(admin.getUsername());
        adminRBucket.set(admin);

        return admin;
    }


    @Override
    public String login(String username, String password) {
        String token = null;
        // 密码需要客户端加密后传递
        try {
            UserDetails userDetails = loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            token = jwtTokenUtil.generateToken(userDetails);
            if (!passwordEncoder.matches(password, userDetails.getPassword())) {
                throw new ServiceException("登录密码错误！");
            }
        } catch (AuthenticationException e) {
            log.warn("登录异常:{}", e.getMessage());
        }
        return token;
    }

    RBucket<Admin> getMemberBucketByUserName(String username) {
        RBucket<Admin> adminRBucket = redisson.getBucket("mall:admin:username:" + username);
        adminRBucket.expire(1, TimeUnit.HOURS);
        return adminRBucket;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Admin admin = getAdminByUsername(username);

        if (admin == null) {
            throw new UsernameNotFoundException("用户名或密码错误！");
        }
        List<AdminResource> resourceList = getResourceList(admin.getId());
        return new AdminUserDetails(admin, resourceList);
    }

    private List<AdminResource> getResourceList(Long userId) {
        List<AdminResource> resourceList = authDao.getResourceList(userId);
        return resourceList;
    }

    @Override
    public String refreshToken(String token) {
        return jwtTokenUtil.refreshHeadToken(token);
    }

    @Override
    public Admin getAdminByUsername(String username) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AdminParam::getUsername, username);
        Admin admin;
        // 缓存中查找
        RBucket<Admin> adminRBucket = getMemberBucketByUserName(username);
        admin = adminRBucket.get();
        if (admin == null) {
            admin = adminDao.selectOne(queryWrapper);
            adminRBucket.set(admin);
        }
        return admin;
    }

    @Override
    public Admin getAdminByToken(String token) {
        String validToken = jwtTokenUtil.validToken(token);
        String username = jwtTokenUtil.getUserNameFromToken(validToken);
        return getAdminByUsername(username);
    }

    @Override
    public List<AdminRole> getRoleList(Long adminId) {
        List<AdminRole> roleList = authDao.getRoleList(adminId);
        return roleList;
    }

    @Override
    public CommonPage<Admin> getPage(String keyword, Integer pageSize, Integer pageNum) {

        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .like(StrUtil.isNotBlank(keyword), AdminParam::getNickName, keyword)
                .like(StrUtil.isNotBlank(keyword), AdminParam::getUsername, keyword);
        IPage<Admin> page = adminDao.selectPage(new Page<>(pageNum, pageSize), queryWrapper);
        return CommonPage.restPage(page);
    }

    @Override
    public Admin getItem(Long id) {
        return adminDao.selectById(id);
    }

    @Override
    public int update(Long id, Admin admin) {
        admin.setId(id);
        // 加盐保存密码
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        int count = adminDao.updateById(admin);
        // 更新后删除缓存
        RBucket<Admin> adminRBucket = getMemberBucketByUserName(admin.getUsername());
        // 考虑延迟双删
        adminRBucket.delete();
        return count;
    }

    @Override
    public int updatePassword(UpdateAdminPasswordParam updatePasswordParam) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(AdminParam::getUsername, updatePasswordParam.getUsername());
        Admin admin = adminDao.selectOne(queryWrapper);

        if (admin == null) {
            throw new ServiceException("查无此人！");
        }

        if (!passwordEncoder.matches(updatePasswordParam.getOldPassword(), admin.getPassword())) {
            throw new ServiceException("原密码错误！");
        }

        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        int count = adminDao.updateById(admin);

        RBucket<Admin> adminRBucket = getMemberBucketByUserName(admin.getUsername());
        adminRBucket.delete();
        return count;
    }

    @Override
    public int delete(Long id) {
        Admin admin = adminDao.selectById(id);
        int count = adminDao.deleteById(id);
        log.debug("删除用户：{}", JSON.toJSONString(admin));
        RBucket<Admin> adminRBucket = getMemberBucketByUserName(admin.getUsername());
        adminRBucket.delete();
        return count;
    }

    @Override
    public int updateRole(Long adminId, List<Long> roleIds) {

        // 先删除原来的
//        LambdaQueryWrapper<AdminRoleRelation> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(AdminRoleRelation::getAdminId, adminId);
        LambdaQueryWrapper<AdminRoleRelation> wrapper = new LambdaQueryWrapper<AdminRoleRelation>()
                .eq(AdminRoleRelation::getAdminId, adminId);

        int deletedCount = adminRoleRelationDao.delete(wrapper);
        log.debug("deletedCount = {}", deletedCount);

        // 再插入新的
        long count = roleIds.stream()
                .map(roleId -> adminRoleRelationDao.insert(new AdminRoleRelation(adminId, roleId)))
                .mapToInt(i -> i)
                .count();

        return (int) count;
    }
}
