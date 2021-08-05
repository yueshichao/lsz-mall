package com.lsz.mall.portal.controller;

import com.lsz.mall.base.entity.Res;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.portal.entity.OrderDetailVO;
import com.lsz.mall.portal.entity.OrderListVO;
import com.lsz.mall.portal.entity.SaveOrderParam;
import com.lsz.mall.portal.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "订单管理")
@RequestMapping("/api/v1")
@Slf4j
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/saveOrder")
    @ApiOperation(value = "生成订单接口")
    public Res<String> saveOrder(@Validated @RequestBody SaveOrderParam saveOrderParam) {
        String orderId = orderService.saveOrder(saveOrderParam);
        return Res.ok(orderId);
    }

    @GetMapping("/order/{orderNo}")
    @ApiOperation(value = "订单详情接口")
    public Res<OrderDetailVO> orderDetailPage(@PathVariable("orderNo") String orderId) {
        OrderDetailVO orderDetailVO = orderService.getDetail(orderId);
        return Res.ok(orderDetailVO);
    }

    @GetMapping("/order")
    @ApiOperation(value = "订单列表接口")
    public Res<CommonPage<OrderListVO>> orderList(
            @RequestParam(defaultValue = "5") Integer pageSize,
            @RequestParam(value = "pageNumber", defaultValue = "1") Integer pageNo,
            @ApiParam(value = "订单状态:0.待支付 1.待确认 2.待发货 3:已发货 4.交易成功") @RequestParam(required = false) Integer status) {
        CommonPage<OrderListVO> page = orderService.getPage(pageNo, pageSize, status);
        return Res.ok(page);

    }

    @PutMapping("/order/{orderNo}/cancel")
    @ApiOperation(value = "订单取消接口")
    public Res cancelOrder(@PathVariable("orderNo") String orderId) {
        int count = orderService.cancelOrder(orderId);
        return Res.ok(count);
    }

    @PutMapping("/order/{orderNo}/finish")
    @ApiOperation(value = "确认收货接口")
    public Res finishOrder(@PathVariable("orderNo") String orderId) {
        int count = orderService.confirmReceive(orderId);
        return Res.ok(count);
    }

    @GetMapping("/paySuccess")
    @ApiOperation(value = "模拟支付成功回调的接口")
    public Res paySuccess(@RequestParam("orderNo") String orderId, @RequestParam("payType") int payType) {
        int count = orderService.paySuccess(orderId, payType);
        return Res.ok(count);
    }


}
