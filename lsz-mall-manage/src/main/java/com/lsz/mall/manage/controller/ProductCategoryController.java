package com.lsz.mall.manage.controller;

import com.alibaba.fastjson.JSON;
import com.lsz.mall.base.entity.ProductCategory;
import com.lsz.mall.base.entity.ProductCategoryParam;
import com.lsz.mall.base.entity.ProductCategoryWithChildrenItem;
import com.lsz.mall.base.entity.ResponseMessage;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.manage.service.ProductCategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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
    @RequestMapping(value = "/page/{parentId}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage<CommonPage<ProductCategory>> getPage(@PathVariable Long parentId,
                                                                @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        CommonPage<ProductCategory> page = productCategoryService.getPage(parentId, pageSize, pageNum);
        return ResponseMessage.ok(page);
    }

    @ApiOperation("分页查询商品父层级")
    @RequestMapping(value = "/list/{currentLevel}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage<List<ProductCategory>> getList(@PathVariable Integer currentLevel,
                                                                @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                                @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        List<ProductCategory> list = productCategoryService.getList(currentLevel);
        return ResponseMessage.ok(list);
    }


    @ApiOperation("查询所有一级分类及子分类")
    @RequestMapping(value = "/list/withChildren", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage listWithChildren() {
        List<ProductCategoryWithChildrenItem> list = productCategoryService.listWithChildren();
        return ResponseMessage.ok(JSON.toJSONString(list));
    }

    @ApiOperation("修改商品分类")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage update(@PathVariable Long id,
                                  @Validated
                                  @RequestBody ProductCategoryParam productCategoryParam) {
        int count = productCategoryService.update(id, productCategoryParam);
        if (count > 0) {
            return ResponseMessage.ok(count);
        } else {
            return ResponseMessage.error();
        }
    }

    @ApiOperation("根据id获取商品分类")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage<ProductCategory> getItem(@PathVariable Long id) {
        ProductCategory productCategory = productCategoryService.getItem(id);
        return ResponseMessage.ok(productCategory);
    }

    @ApiOperation("删除商品分类")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage delete(@PathVariable Long id) {
        int count = productCategoryService.delete(id);
        if (count > 0) {
            return ResponseMessage.ok(count);
        } else {
            return ResponseMessage.error();
        }
    }

    @ApiOperation("修改导航栏显示状态")
    @RequestMapping(value = "/update/navStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage updateNavStatus(@RequestParam("ids") List<Long> ids, @RequestParam("navStatus") Integer navStatus) {
        int count = productCategoryService.updateNavStatus(ids, navStatus);
        if (count > 0) {
            return ResponseMessage.ok(count);
        } else {
            return ResponseMessage.error();
        }
    }

    @ApiOperation("修改显示状态")
    @RequestMapping(value = "/update/showStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage updateShowStatus(@RequestParam("ids") List<Long> ids, @RequestParam("showStatus") Integer showStatus) {
        int count = productCategoryService.updateShowStatus(ids, showStatus);
        if (count > 0) {
            return ResponseMessage.ok(count);
        } else {
            return ResponseMessage.error();
        }
    }

    @ApiOperation("修改商品分类")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage update(@PathVariable Long id,
                                  @Validated
                                  @RequestBody ProductCategoryParam productCategoryParam) {
        int count = productCategoryService.update(id, productCategoryParam);
        if (count > 0) {
            return ResponseMessage.ok(count);
        } else {
            return ResponseMessage.error();
        }
    }

    @ApiOperation("根据id获取商品分类")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage<ProductCategory> getItem(@PathVariable Long id) {
        ProductCategory productCategory = productCategoryService.getItem(id);
        return ResponseMessage.ok(productCategory);
    }

    @ApiOperation("删除商品分类")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage delete(@PathVariable Long id) {
        int count = productCategoryService.delete(id);
        if (count > 0) {
            return ResponseMessage.ok(count);
        } else {
            return ResponseMessage.error();
        }
    }

    @ApiOperation("修改导航栏显示状态")
    @RequestMapping(value = "/update/navStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage updateNavStatus(@RequestParam("ids") List<Long> ids, @RequestParam("navStatus") Integer navStatus) {
        int count = productCategoryService.updateNavStatus(ids, navStatus);
        if (count > 0) {
            return ResponseMessage.ok(count);
        } else {
            return ResponseMessage.error();
        }
    }

    @ApiOperation("修改显示状态")
    @RequestMapping(value = "/update/showStatus", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage updateShowStatus(@RequestParam("ids") List<Long> ids, @RequestParam("showStatus") Integer showStatus) {
        int count = productCategoryService.updateShowStatus(ids, showStatus);
        if (count > 0) {
            return ResponseMessage.ok(count);
        } else {
            return ResponseMessage.error();
        }
    }

}
