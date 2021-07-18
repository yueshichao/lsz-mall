package com.lsz.mall.manage.controller;

import com.lsz.mall.base.entity.OrderSetting;
import com.lsz.mall.base.entity.ResponseMessage;
import com.lsz.mall.manage.service.OrderSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@Api(tags = "OrderSettingController", description = "订单设置管理")
@RequestMapping("/orderSetting")
public class OrderSettingController {
    @Autowired
    private OrderSettingService orderSettingService;

    @ApiOperation("获取指定订单设置")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage<OrderSetting> getItem(@PathVariable Long id) {
        OrderSetting orderSetting = orderSettingService.getItem(id);
        return ResponseMessage.ok(orderSetting);
    }

    @ApiOperation("修改指定订单设置")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage update(@PathVariable Long id, @RequestBody OrderSetting orderSetting) {
        int count = orderSettingService.update(id, orderSetting);
        if (count > 0) {
            return ResponseMessage.ok(count);
        }
        return ResponseMessage.error();
    }
}
