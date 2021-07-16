package com.lsz.mall.manage.controller;

import com.lsz.mall.base.entity.AdminResource;
import com.lsz.mall.base.entity.ResponseMessage;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.manage.config.DynamicSecurityMetadataSource;
import com.lsz.mall.manage.service.AdminResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@Api(tags = "ResourceController", description = "后台资源管理")
@RequestMapping("/resource")
public class ResourceController {

    @Autowired
    private AdminResourceService resourceService;
    @Autowired
    private DynamicSecurityMetadataSource dynamicSecurityMetadataSource;

    @ApiOperation("添加后台资源")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage create(@RequestBody AdminResource adminResource) {
        int count = resourceService.create(adminResource);
        dynamicSecurityMetadataSource.clearDataSource();
        if (count > 0) {
            return ResponseMessage.ok(count);
        } else {
            return ResponseMessage.error();
        }
    }

    @ApiOperation("修改后台资源")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage update(@PathVariable Long id,
                                  @RequestBody AdminResource adminResource) {
        int count = resourceService.update(id, adminResource);
        dynamicSecurityMetadataSource.clearDataSource();
        if (count > 0) {
            return ResponseMessage.ok(count);
        } else {
            return ResponseMessage.error();
        }
    }

    @ApiOperation("根据ID获取资源详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage<AdminResource> getItem(@PathVariable Long id) {
        AdminResource adminResource = resourceService.getItem(id);
        return ResponseMessage.ok(adminResource);
    }

    @ApiOperation("根据ID删除后台资源")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage delete(@PathVariable Long id) {
        int count = resourceService.delete(id);
        dynamicSecurityMetadataSource.clearDataSource();
        if (count > 0) {
            return ResponseMessage.ok(count);
        } else {
            return ResponseMessage.error();
        }
    }

    @ApiOperation("分页模糊查询后台资源")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage<CommonPage<AdminResource>> list(@RequestParam(required = false) Long categoryId,
                                                           @RequestParam(required = false) String nameKeyword,
                                                           @RequestParam(required = false) String urlKeyword,
                                                           @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        CommonPage<AdminResource> resourceList = resourceService.getPage(categoryId, nameKeyword, urlKeyword, pageSize, pageNum);
        return ResponseMessage.ok(resourceList);
    }

    @ApiOperation("查询所有后台资源")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage<List<AdminResource>> listAll() {
        List<AdminResource> resourceList = resourceService.listAll();
        return ResponseMessage.ok(resourceList);
    }
}
