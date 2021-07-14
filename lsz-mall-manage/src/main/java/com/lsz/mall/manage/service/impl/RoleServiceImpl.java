package com.lsz.mall.manage.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsz.mall.base.entity.*;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.manage.dao.*;
import com.lsz.mall.manage.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    AdminMenuDao menuDao;

    @Autowired
    AdminRoleDao adminRoleDao;

    @Autowired
    AuthDao authDao;

    @Override
    public List<AdminMenu> getMenuList(Long adminId) {
        return authDao.getMenuList(adminId);
    }

    @Override
    public int create(AdminRole role) {
        role.setCreateTime(new Date());
        role.setAdminCount(0);
        role.setSort(0);
        return adminRoleDao.insert(role);
    }

    @Override
    public int update(Long id, AdminRole role) {
        role.setId(id);
        return adminRoleDao.updateById(role);
    }

    @Override
    public int delete(List<Long> ids) {
        return adminRoleDao.deleteBatchIds(ids);
    }

    @Override
    public List<AdminRole> list() {
        return adminRoleDao.selectList(null);
    }

    @Override
    public CommonPage<AdminRole> getPage(String keyword, Integer pageSize, Integer pageNum) {
        LambdaQueryWrapper<AdminRole> queryWrapper = new LambdaQueryWrapper<AdminRole>()
                .like(StrUtil.isNotBlank(keyword), AdminRole::getName, keyword);
        IPage<AdminRole> page = adminRoleDao.selectPage(new Page<>(pageNum, pageSize), queryWrapper);
        return CommonPage.restPage(page);
    }

    @Override
    public List<AdminMenu> listMenu(Long roleId) {
        return authDao.getMenuListByRoleId(roleId);
    }

    @Override
    public List<AdminResource> listResource(Long roleId) {
        return authDao.getResourceListByRoleId(roleId);
    }

    @Autowired
    AdminRoleMenuRelationDao roleMenuRelationDao;

    @Autowired
    AdminRoleResourceRelationDao roleResourceRelationDao;

    @Override
    public int allocMenu(Long roleId, List<Long> menuIds) {
        LambdaQueryWrapper<AdminRoleMenuRelation> wrapper = new LambdaQueryWrapper<AdminRoleMenuRelation>()
                .eq(AdminRoleMenuRelation::getRoleId, roleId);
        int deleteCount = roleMenuRelationDao.delete(wrapper);
        log.debug("deleteCount = {}", deleteCount);

        int count = Optional.ofNullable(menuIds)
                .orElseGet(ArrayList::new)
                .stream()
                .map(menuId -> new AdminRoleMenuRelation(roleId, menuId))
                .map(relation -> {
                    return roleMenuRelationDao.insert(relation);
                })
                .mapToInt(i -> i)
                .sum();
        return count;
    }

    @Override
    public int allocResource(Long roleId, List<Long> resourceIds) {
        LambdaQueryWrapper<AdminRoleResourceRelation> wrapper = new LambdaQueryWrapper<AdminRoleResourceRelation>()
                .eq(AdminRoleResourceRelation::getRoleId, roleId);
        roleResourceRelationDao.delete(wrapper);

        int count = Optional.ofNullable(resourceIds)
                .orElseGet(ArrayList::new)
                .stream()
                .map(menuId -> new AdminRoleResourceRelation(roleId, menuId))
                .map(relation -> {
                    return roleResourceRelationDao.insert(relation);
                })
                .mapToInt(i -> i)
                .sum();
        return count;
    }
}
