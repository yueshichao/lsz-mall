package com.lsz.mall.portal.config;

import com.lsz.mall.base.entity.Member;
import com.lsz.mall.portal.annotation.Token2User;
import com.lsz.mall.portal.service.UserService;
import com.lsz.mall.portal.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@Slf4j
public class Token2UserArgResolver implements HandlerMethodArgumentResolver {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Token2User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Token2User token2User = parameter.getParameterAnnotation(Token2User.class);
        Member member = new Member();
        String token = webRequest.getHeader("token");


        return member;
    }
}
