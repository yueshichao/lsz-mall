package com.lsz.mall.manage.controller;

import com.lsz.mall.base.entity.ResponseMessage;
import com.lsz.mall.manage.service.ProductAttrService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(tags = "PmsProductAttributeCategoryController", description = "商品属性分类管理")
@RequestMapping("/productAttribute/category")
public class ProductAttrController {

    @Autowired
    ProductAttrService productAttrService;

    @ApiOperation("添加商品属性分类")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage create(@RequestParam String name) {
        int count = productAttrService.create(name);
        if (count > 0) {
            return ResponseMessage.ok();
        } else {
            return ResponseMessage.error();
        }
    }





}
