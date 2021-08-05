package com.lsz.mall.portal.service;

import com.lsz.mall.base.entity.Member;
import com.lsz.mall.base.param.UpdateUserInfoParam;
import com.lsz.mall.base.param.UserRegisterParam;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    String login(UserRegisterParam userLoginParam);

    Boolean logout(UserRegisterParam userLoginParam);

    @Transactional
    String register(UserRegisterParam userRegisterParam);

    UserDetails loadUserByUsername(String username);

    /**
     * 获取当前登录会员
     */
    Member getCurrentMember();

    @Transactional
    int updateInfo(UpdateUserInfoParam userInfoParam);

}
