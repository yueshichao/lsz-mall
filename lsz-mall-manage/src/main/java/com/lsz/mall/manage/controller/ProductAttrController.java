package com.lsz.mall.manage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lsz.mall.base.entity.ProductAttribute;
import com.lsz.mall.base.entity.ProductAttributeParam;
import com.lsz.mall.base.entity.ResponseMessage;
import com.lsz.mall.base.vo.CommonPage;
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

    @ApiOperation("根据分类查询属性列表或参数列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "type", value = "0表示属性，1表示参数", required = true, paramType = "query", dataType = "integer")})
    @RequestMapping(value = "/list/{cid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage getList(@PathVariable Long cid,
                                                                 @RequestParam(value = "type") Integer type,
                                                                 @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                                 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
//        List<ProductAttribute> productAttributeList = productAttributeService.getPage(cid, type, pageSize, pageNum);
//        return ResponseMessage.ok(CommonPage.restPage(productAttributeList));

        IPage<ProductAttribute> page = productAttributeService.getPage(cid, type, pageSize, pageNum);
        return ResponseMessage.ok(CommonPage.restPage(page));
//        return ResponseMessage.ok(page);
    }

}
