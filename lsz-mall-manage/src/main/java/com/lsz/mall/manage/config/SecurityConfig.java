package com.lsz.mall.manage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.List;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    SecureProperties secureProperties;

    @Override
    public void configure(WebSecurity webSecurity) {
        // spring security + swagger2 参考：https://www.cnblogs.com/softidea/p/6295115.html
//        webSecurity.ignoring().antMatchers("/v2/api-docs",//swagger api json
//                "/swagger-resources/configuration/ui",//用来获取支持的动作
//                "/swagger-resources",//用来获取api-docs的URI
//                "/swagger-resources/configuration/security",//安全选项
//                "/swagger-ui.html");

        List<String> permitUrls = secureProperties.getPermitUrls();
        webSecurity.ignoring().antMatchers(permitUrls.toArray(new String[permitUrls.size()]));

    }
}
