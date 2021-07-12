package com.lsz.mall.manage.controller;

import com.lsz.mall.base.entity.Product;
import com.lsz.mall.base.entity.ProductParam;
import com.lsz.mall.base.entity.ProductQueryParam;
import com.lsz.mall.base.entity.ResponseMessage;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.manage.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Api(description = "商品管理")
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @ApiOperation("创建商品")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage create(@RequestBody ProductParam productParam) {
        int count = productService.create(productParam);
        if (count > 0) {
            return ResponseMessage.ok(count);
        } else {
            return ResponseMessage.error();
        }
    }


    @ApiOperation("查询商品")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage<CommonPage<Product>> getList(ProductQueryParam productQueryParam,
                                                        @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                        @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        CommonPage<Product> productList = productService.list(productQueryParam, pageSize, pageNum);
        return ResponseMessage.ok(productList);
    }


}
