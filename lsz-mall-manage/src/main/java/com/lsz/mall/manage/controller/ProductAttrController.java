package com.lsz.mall.manage.controller;

import com.lsz.mall.base.entity.ProductAttributeParam;
import com.lsz.mall.base.entity.ResponseMessage;
import com.lsz.mall.manage.service.ProductAttrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Api(description = "商品属性管理")
@RequestMapping("/productAttribute")
public class ProductAttrController {

    @Autowired
    private ProductAttrService productAttributeService;

    @ApiOperation("添加商品属性信息")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage create(@RequestBody ProductAttributeParam productAttributeParam) {
        int count = productAttributeService.create(productAttributeParam);
        if (count > 0) {
            return ResponseMessage.ok();
        } else {
            return ResponseMessage.error();
        }
    }

}
