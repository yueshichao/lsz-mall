package com.lsz.mall.manage.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Slf4j
@Data
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "secure")
public class SecureProperties {

    @PostConstruct
    public void f() {
        log.info("permitUrls = {}", permitUrls);
    }

    private List<String> permitUrls;

}
