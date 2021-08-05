package com.lsz.mall.portal.controller;

import com.lsz.mall.base.entity.Res;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.portal.entity.ProductDetailVO;
import com.lsz.mall.portal.entity.ProductSearchGoodsVO;
import com.lsz.mall.portal.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@Api(tags = "商品页")
@RequestMapping("/api/v1")
@Slf4j
public class ProductController {

    @Resource
    private ProductService productService;

    @GetMapping("/search")
    @ApiOperation(value = "商品搜索接口")
    public Res<CommonPage<ProductSearchGoodsVO>> search(
            @RequestParam(required = false) @ApiParam(value = "搜索关键字") String keyword,
            @RequestParam(required = false) @ApiParam(value = "分类id") Long goodsCategoryId,
            @RequestParam(required = false) @ApiParam(value = "orderBy") String orderBy,
            @RequestParam Integer pageNumber,
            @RequestParam(defaultValue = "10") Integer pageSize
    ) {

        CommonPage<ProductSearchGoodsVO> page = productService.search(keyword, goodsCategoryId, orderBy, pageNumber, pageSize);
        return Res.ok(page);
    }

    @GetMapping("/goods/detail/{goodsId}")
    @ApiOperation(value = "商品详情接口")
    public Res<ProductDetailVO> goodsDetail(
            @ApiParam(value = "商品id") @PathVariable("goodsId") Long goodsId
    ) {
        ProductDetailVO productDetailVO = productService.getDetail(goodsId);
        return Res.ok(productDetailVO);
    }


}
