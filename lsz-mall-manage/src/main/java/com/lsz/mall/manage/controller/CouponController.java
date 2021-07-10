package com.lsz.mall.manage.controller;

import com.lsz.mall.base.entity.Coupon;
import com.lsz.mall.base.entity.CouponParam;
import com.lsz.mall.base.entity.ResponseMessage;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.manage.service.CouponService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@Api(description = "优惠券管理")
@RequestMapping("/coupon")
public class CouponController {
    @Autowired
    private CouponService couponService;

    @ApiOperation("添加优惠券")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage add(@RequestBody CouponParam couponParam) {
        int count = couponService.create(couponParam);
        if (count > 0) {
            return ResponseMessage.ok(count);
        }
        return ResponseMessage.error();
    }

    @ApiOperation("根据优惠券名称和类型分页获取优惠券列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage<CommonPage<Coupon>> list(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        CommonPage<Coupon> page = couponService.getPage(name, type, pageSize, pageNum);
        return ResponseMessage.ok(page);
    }

}
