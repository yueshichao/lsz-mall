package com.lsz.mall.manage.controller;

import com.lsz.mall.base.entity.Brand;
import com.lsz.mall.base.entity.BrandParam;
import com.lsz.mall.base.entity.ResponseMessage;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.manage.service.BrandService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(description = "商品品牌管理")
@RequestMapping("/brand")
public class BrandController {

    @Autowired
    private BrandService brandService;


    @ApiOperation(value = "添加品牌")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage create(@Validated @RequestBody BrandParam Brand) {
        ResponseMessage responseMessage;
        int count = brandService.createBrand(Brand);
        if (count == 1) {
            responseMessage = ResponseMessage.ok(count);
        } else {
            responseMessage = ResponseMessage.error();
        }
        return responseMessage;
    }

    @ApiOperation(value = "根据品牌名称分页获取品牌列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage<CommonPage<Brand>> getList(@RequestParam(value = "keyword", required = false) String keyword,
                                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                      @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        CommonPage<Brand> brandList = brandService.getBrandPage(keyword, pageNum, pageSize);
        return ResponseMessage.ok(brandList);
    }


}
