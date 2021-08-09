package com.lsz.mall.portal.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lsz.mall.base.entity.Member;
import com.lsz.mall.base.entity.ServiceException;
import com.lsz.mall.base.param.UpdateUserInfoParam;
import com.lsz.mall.base.param.UserRegisterParam;
import com.lsz.mall.portal.dao.MemberDao;
import com.lsz.mall.portal.entity.MemberDetails;
import com.lsz.mall.portal.service.UserService;
import com.lsz.mall.portal.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    MemberDao memberDao;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RedissonClient redisson;

    @Override
    public String login(UserRegisterParam userLoginParam) {
        LambdaQueryWrapper<Member> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Member::getUsername, userLoginParam.getLoginName());
        Member member = memberDao.selectOne(wrapper);

        if (member == null) {
            throw new ServiceException("用户不存在！");
        }

        if (!passwordEncoder.matches(userLoginParam.getPassword(), member.getPassword())) {
            throw new ServiceException("密码不正确！");
        }

        MemberDetails memberDetails = new MemberDetails(member);
        String token = jwtTokenUtil.generateToken(memberDetails);
        // 设置缓存
        RBucket<Member> userBucket = getUserBucketByUsername(member.getUsername());
        userBucket.set(member);

        return token;
    }

    private RBucket<Member> getUserBucketByUsername(String username) {
        RBucket<Member> userBucket = redisson.getBucket("mall:user:id:" + username);
        return userBucket;
    }

    @Override
    public Boolean logout(UserRegisterParam userLoginParam) {
        log.info("退出登录：{}", JSON.toJSONString(userLoginParam));
        return true;
    }

    @Override
    public String register(UserRegisterParam userRegisterParam) {
        Member member = new Member();
        member.setUsername(userRegisterParam.getLoginName());
        member.setNickname(userRegisterParam.getLoginName());
        member.setPassword(passwordEncoder.encode(userRegisterParam.getPassword()));
        member.setCreateTime(new Date());
        int insertCount = 0;
        try {
            insertCount = memberDao.insert(member);
        } catch (DuplicateKeyException e) {
            throw new ServiceException("用户名或手机号重复！");
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
        if (insertCount <= 0) {
            throw new ServiceException("注册失败！");
        }
        RBucket<Member> userBucket = getUserBucketByUsername(member.getUsername());
        userBucket.set(member);
        return "注册成功！";
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Member member = getMemberByUsername(username);

        MemberDetails memberDetails = new MemberDetails(member);
        return memberDetails;
    }

    private Member getMemberByUsername(String username) {
        Member member;
        RBucket<Member> memberBucket = getUserBucketByUsername(username);
        member = memberBucket.get();
        if (member != null) {
            return member;
        }
        LambdaQueryWrapper<Member> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Member::getUsername, username);
        member = memberDao.selectOne(wrapper);
        memberBucket.set(member, 5, TimeUnit.HOURS);
        return member;
    }

    @Override
    public Member getCurrentMember() {
        try {
            SecurityContext ctx = SecurityContextHolder.getContext();
            Authentication auth = ctx.getAuthentication();
            return Optional.ofNullable(auth)
                    .map(Authentication::getPrincipal)
                    .map(c -> (MemberDetails) c)
                    .map(MemberDetails::getMember)
                    .orElse(null);
        } catch (Exception e) {
            log.error("{}", e.toString());
            throw new ServiceException("未登录或权限过期！", "401");
        }
    }

    @Override
    public int updateInfo(UpdateUserInfoParam userInfoParam) {
        Member preMember = getCurrentMember();
        Member currentMember = preMember.clone();
        currentMember.setUsername(userInfoParam.getLoginName());
        currentMember.setNickname(userInfoParam.getNickName());

        String newPassword = userInfoParam.getPassword();
        if (StrUtil.isNotBlank(newPassword)) {
            currentMember.setPassword(passwordEncoder.encode(newPassword));
        }
        currentMember.setPersonalizedSignature(userInfoParam.getIntroduceSign());
        int count = memberDao.updateById(currentMember);

        RBucket<Member> preUserBucket = getUserBucketByUsername(preMember.getUsername());
        preUserBucket.delete();

        return count;
    }
}
