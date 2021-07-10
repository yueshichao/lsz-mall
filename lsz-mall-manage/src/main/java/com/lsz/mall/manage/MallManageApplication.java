package com.lsz.mall.manage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
@MapperScan("com.lsz.mall.manage.dao")
public class MallManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallManageApplication.class, args);
    }

}
