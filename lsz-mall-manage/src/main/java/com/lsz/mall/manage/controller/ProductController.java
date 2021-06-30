package com.lsz.mall.manage.controller;

import cn.hutool.core.date.DateUtil;
import com.lsz.mall.base.entity.Product;
import com.lsz.mall.base.entity.ResponseMessage;
import com.lsz.mall.manage.dao.ProductDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductDao productDao;

    @GetMapping
    public ResponseMessage get() {

        log.info("{}", DateUtil.now());
        Product product = productDao.findById(1);
        return ResponseMessage.ok(product);
    }


}
