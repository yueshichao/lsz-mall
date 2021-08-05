package com.lsz.mall.portal.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lsz.mall.base.entity.Member;
import com.lsz.mall.base.entity.ServiceException;
import com.lsz.mall.base.param.UpdateUserInfoParam;
import com.lsz.mall.base.param.UserRegisterParam;
import com.lsz.mall.portal.dao.MemberDao;
import com.lsz.mall.portal.entity.MemberDetails;
import com.lsz.mall.portal.service.UserService;
import com.lsz.mall.portal.util.JwtTokenUtil;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
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

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    MemberDao memberDao;

    @Autowired
    PasswordEncoder passwordEncoder;

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
        return token;
    }

    @Override
    public Boolean logout(UserRegisterParam userLoginParam) {
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
        return "注册成功！";
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        LambdaQueryWrapper<Member> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Member::getUsername, username);
        Member member = memberDao.selectOne(wrapper);

        MemberDetails memberDetails = new MemberDetails(member);
        return memberDetails;
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
        Member currentMember = getCurrentMember();
        currentMember.setUsername(userInfoParam.getLoginName());
        currentMember.setNickname(userInfoParam.getNickName());

        String newPassword = userInfoParam.getPassword();
        if (StrUtil.isNotBlank(newPassword)) {
            currentMember.setPassword(passwordEncoder.encode(newPassword));
        }
        currentMember.setPersonalizedSignature(userInfoParam.getIntroduceSign());
        return memberDao.updateById(currentMember);
    }
}
