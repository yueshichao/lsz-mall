package com.lsz.mall.manage.controller;

import com.lsz.mall.base.entity.HomeAdvertise;
import com.lsz.mall.base.entity.ResponseMessage;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.manage.service.HomeAdvertiseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
* AdBlock插件会拦截广告页面信息
* 参考：https://blog.csdn.net/Nalaluky/article/details/82798252
*
* */
@RestController
@Api(tags = "首页轮播广告管理")
@RequestMapping("/home/advertise")
public class HomeAdvertiseController {

    @Autowired
    private HomeAdvertiseService advertiseService;

    @ApiOperation("添加广告")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseMessage create(@RequestBody HomeAdvertise advertise) {
        int count = advertiseService.create(advertise);
        if (count > 0)
            return ResponseMessage.ok(count);
        return ResponseMessage.error();
    }

    @ApiOperation("删除广告")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)

    public ResponseMessage delete(@RequestParam("ids") List<Long> ids) {
        int count = advertiseService.delete(ids);
        if (count > 0)
            return ResponseMessage.ok(count);
        return ResponseMessage.error();
    }

    @ApiOperation("修改上下线状态")
    @RequestMapping(value = "/update/status/{id}", method = RequestMethod.POST)

    public ResponseMessage updateStatus(@PathVariable Long id, Integer status) {
        int count = advertiseService.updateStatus(id, status);
        if (count > 0)
            return ResponseMessage.ok(count);
        return ResponseMessage.error();
    }

    @ApiOperation("获取广告详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)

    public ResponseMessage<HomeAdvertise> getItem(@PathVariable Long id) {
        HomeAdvertise advertise = advertiseService.getItem(id);
        return ResponseMessage.ok(advertise);
    }

    @ApiOperation("修改广告")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)

    public ResponseMessage update(@PathVariable Long id, @RequestBody HomeAdvertise advertise) {
        int count = advertiseService.update(id, advertise);
        if (count > 0)
            return ResponseMessage.ok(count);
        return ResponseMessage.error();
    }

    @ApiOperation("分页查询广告")
    @RequestMapping(value = "/list", method = RequestMethod.GET)

    public ResponseMessage<CommonPage<HomeAdvertise>> list(@RequestParam(value = "name", required = false) String name,
                                                           @RequestParam(value = "type", required = false) Integer type,
                                                           @RequestParam(value = "endTime", required = false) String endTime,
                                                           @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                           @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        CommonPage<HomeAdvertise> advertiseList = advertiseService.list(name, type, endTime, pageSize, pageNum);
        return ResponseMessage.ok(advertiseList);
    }

}
