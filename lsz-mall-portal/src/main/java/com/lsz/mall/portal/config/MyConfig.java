package com.lsz.mall.portal.config;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.lsz.mall.portal.service.IdService;
import com.lsz.mall.portal.util.JwtTokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyConfig {

    // mybatis-plus分页插件
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil();
    }

    @Bean
    public IdService idService() {
        Snowflake snowflake = IdUtil.createSnowflake(0, 0L);
        return new IdService() {
            @Override
            public Long generateOrderId() {
                return snowflake.nextId();
            }
        };
    }

}
