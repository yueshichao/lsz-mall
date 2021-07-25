package com.lsz.mall.manage.controller;

import com.lsz.mall.base.entity.*;
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
        CommonPage<Product> productList = productService.getPage(productQueryParam, pageSize, pageNum);
        return ResponseMessage.ok(productList);
    }


    @ApiOperation("根据商品id获取商品编辑信息")
    @RequestMapping(value = "/updateInfo/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage<ProductResult> getUpdateInfo(@PathVariable Long id) {
        ProductResult productResult = productService.getUpdateInfo(id);
        return ResponseMessage.ok(productResult);
    }

    @ApiOperation("更新商品")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage update(@PathVariable Long id, @RequestBody ProductParam productParam) {
        int count = productService.update(id, productParam);
        if (count > 0) {
            return ResponseMessage.ok(count);
        } else {
            return ResponseMessage.error();
        }
    }


    @ApiOperation("根据商品名称或货号模糊查询")
    @RequestMapping(value = "/simpleList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage<List<Product>> getList(String keyword) {
        List<Product> productList = productService.list(keyword);
        return ResponseMessage.ok(productList);
    }

    @ApiOperation("批量修改审核状态")
    @RequestMapping(value = "/update/verifyStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage updateVerifyStatus(@RequestParam("ids") List<Long> ids,
                                              @RequestParam("verifyStatus") Integer verifyStatus,
                                              @RequestParam("detail") String detail) {
        int count = productService.updateVerifyStatus(ids, verifyStatus, detail);
        if (count > 0) {
            return ResponseMessage.ok(count);
        } else {
            return ResponseMessage.error();
        }
    }

    @ApiOperation("批量上下架")
    @RequestMapping(value = "/update/publishStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage updatePublishStatus(@RequestParam("ids") List<Long> ids,
                                               @RequestParam("publishStatus") Integer publishStatus) {
        int count = productService.updatePublishStatus(ids, publishStatus);
        if (count > 0) {
            return ResponseMessage.ok(count);
        } else {
            return ResponseMessage.error();
        }
    }

    @ApiOperation("批量推荐商品")
    @RequestMapping(value = "/update/recommendStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage updateRecommendStatus(@RequestParam("ids") List<Long> ids,
                                                 @RequestParam("recommendStatus") Integer recommendStatus) {
        int count = productService.updateRecommendStatus(ids, recommendStatus);
        if (count > 0) {
            return ResponseMessage.ok(count);
        } else {
            return ResponseMessage.error();
        }
    }

    @ApiOperation("批量设为新品")
    @RequestMapping(value = "/update/newStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage updateNewStatus(@RequestParam("ids") List<Long> ids,
                                           @RequestParam("newStatus") Integer newStatus) {
        int count = productService.updateNewStatus(ids, newStatus);
        if (count > 0) {
            return ResponseMessage.ok(count);
        } else {
            return ResponseMessage.error();
        }
    }

    @ApiOperation("批量修改删除状态")
    @RequestMapping(value = "/update/deleteStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage updateDeleteStatus(@RequestParam("ids") List<Long> ids,
                                              @RequestParam("deleteStatus") Integer deleteStatus) {
        int count = productService.updateDeleteStatus(ids, deleteStatus);
        if (count > 0) {
            return ResponseMessage.ok(count);
        } else {
            return ResponseMessage.error();
        }
    }


}
