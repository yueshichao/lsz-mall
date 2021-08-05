package com.lsz.mall.manage.controller;

import com.lsz.mall.base.entity.ResponseMessage;
import com.lsz.mall.base.entity.SkuStock;
import com.lsz.mall.manage.service.SkuStockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "sku商品库存管理")
@RequestMapping("/sku")
public class SkuStockController {

    @Autowired
    private SkuStockService skuStockService;

    @ApiOperation("根据商品编号及sku编码模糊搜索sku库存")
    @RequestMapping(value = "/{pid}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage<List<SkuStock>> getList(@PathVariable Long pid, @RequestParam(value = "keyword", required = false) String keyword) {
        List<SkuStock> skuStockList = skuStockService.getList(pid, keyword);
        return ResponseMessage.ok(skuStockList);
    }

    @ApiOperation("批量更新sku库存信息")
    @RequestMapping(value = "/update/{pid}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage update(@PathVariable Long pid, @RequestBody List<SkuStock> skuStockList) {
        int count = skuStockService.update(pid, skuStockList);
        if (count > 0) {
            return ResponseMessage.ok(count);
        } else {
            return ResponseMessage.error();
        }
    }

}
