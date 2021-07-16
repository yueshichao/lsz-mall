package com.lsz.mall.manage.controller;

import com.lsz.mall.base.entity.ProductAttributeCategory;
import com.lsz.mall.base.entity.ProductAttributeCategoryItem;
import com.lsz.mall.base.entity.ResponseMessage;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.manage.service.ProductAttrCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Api(description = "商品属性分类管理")
@RequestMapping("/productAttribute/category")
public class ProductAttrCategoryController {

    @Autowired
    ProductAttrCategoryService productAttrCategoryService;

    @ApiOperation("添加商品属性分类")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage create(@RequestParam String name) {
        int count = productAttrCategoryService.create(name);
        if (count > 0) {
            return ResponseMessage.ok();
        } else {
            return ResponseMessage.error();
        }
    }

    @ApiOperation("修改商品属性分类")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage update(@PathVariable Long id, @RequestParam String name) {
        int count = productAttrCategoryService.update(id, name);
        if (count > 0) {
            return ResponseMessage.ok(count);
        } else {
            return ResponseMessage.error();
        }
    }

    @ApiOperation("删除单个商品属性分类")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage delete(@PathVariable Long id) {
        int count = productAttrCategoryService.delete(id);
        if (count > 0) {
            return ResponseMessage.ok(count);
        } else {
            return ResponseMessage.error();
        }
    }

    @ApiOperation("获取单个商品属性分类信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage<ProductAttributeCategory> getItem(@PathVariable Long id) {
        ProductAttributeCategory productAttributeCategory = productAttrCategoryService.getItem(id);
        return ResponseMessage.ok(productAttributeCategory);
    }

    @ApiOperation("分页获取所有商品属性分类")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage<CommonPage<ProductAttributeCategory>> getList(@RequestParam(defaultValue = "5") Integer pageSize, @RequestParam(defaultValue = "1") Integer pageNum) {
        CommonPage<ProductAttributeCategory> productAttributeCategoryList = productAttrCategoryService.getPage(pageSize, pageNum);
        return ResponseMessage.ok(productAttributeCategoryList);
    }

    @ApiOperation("获取所有商品属性分类及其下属性")
    @RequestMapping(value = "/list/withAttr", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage<List<ProductAttributeCategoryItem>> getListWithAttr() {
        List<ProductAttributeCategoryItem> productAttributeCategoryResultList = productAttrCategoryService.getListWithAttr();
        return ResponseMessage.ok(productAttributeCategoryResultList);
    }


}
