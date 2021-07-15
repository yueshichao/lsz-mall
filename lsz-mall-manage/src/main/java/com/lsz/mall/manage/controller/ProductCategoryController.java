package com.lsz.mall.manage.controller;

import com.lsz.mall.base.entity.ProductCategory;
import com.lsz.mall.base.entity.ProductCategoryParam;
import com.lsz.mall.base.entity.ProductCategoryWithChildrenItem;
import com.lsz.mall.base.entity.ResponseMessage;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.manage.service.ProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Api(description = "商品分类管理")
@RequestMapping("/productCategory")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @ApiOperation("添加产品分类")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage create(@Validated @RequestBody ProductCategoryParam productCategoryParam) {
        int count = productCategoryService.create(productCategoryParam);
        if (count > 0) {
            return ResponseMessage.ok(count);
        } else {
            return ResponseMessage.error();
        }
    }

    @ApiOperation("分页查询商品分类")
    @RequestMapping(value = "/list/{parentId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage<CommonPage<ProductCategory>> getList(@PathVariable Long parentId,
                                                             @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                             @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        CommonPage<ProductCategory> page = productCategoryService.getPage(parentId, pageSize, pageNum);
        return ResponseMessage.ok(page);
    }


    @ApiOperation("查询所有一级分类及子分类")
    @RequestMapping(value = "/list/withChildren", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage<List<ProductCategoryWithChildrenItem>> listWithChildren() {
        List<ProductCategoryWithChildrenItem> list = productCategoryService.listWithChildren();
        return ResponseMessage.ok(list);
    }

}
