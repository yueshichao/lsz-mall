package com.lsz.mall.portal.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Data
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "secure")
public class SecureProperties {

    private List<String> permitUrls;

}
