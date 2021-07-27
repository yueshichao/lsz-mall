package com.lsz.mall.portal.controller;

import com.lsz.mall.base.entity.Res;
import com.lsz.mall.portal.entity.SaveAddressParam;
import com.lsz.mall.portal.entity.UserAddressVO;
import com.lsz.mall.portal.service.UserAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "用户地址管理")
@RequestMapping("/api/v1")
public class UserAddressController {

    @Autowired
    private UserAddressService userAddressService;

    @GetMapping("/address")
    @ApiOperation(value = "我的收货地址列表")
    public Res<List<UserAddressVO>> addressList() {
        List<UserAddressVO> list = userAddressService.findAll();
        return Res.ok(list);
    }

    @PostMapping("/address")
    @ApiOperation(value = "添加地址")
    public Res<Boolean> saveAddress(@Validated @RequestBody SaveAddressParam saveAddressParam) {
        int count = userAddressService.save(saveAddressParam);
        return Res.ok(count > 0);
    }

    @PutMapping("/address")
    @ApiOperation(value = "修改地址")
    public Res<Boolean> updateAddress(@RequestBody UserAddressVO updateAddressParam) {
        int count = userAddressService.update(updateAddressParam);
        return Res.ok(count > 0);
    }

    @GetMapping("/address/{addressId}")
    @ApiOperation(value = "获取收货地址详情")
    public Res<UserAddressVO> getAddress(@PathVariable("addressId") Long addressId) {
        UserAddressVO userAddressVO = userAddressService.get(addressId);
        return Res.ok(userAddressVO);
    }

    @GetMapping("/address/default")
    @ApiOperation(value = "获取默认收货地址")
    public Res<UserAddressVO> getDefaultAddress() {
        UserAddressVO defaultAddress = userAddressService.getDefaultAddress();
        return Res.ok(defaultAddress);
    }

    @DeleteMapping("/address/{addressId}")
    @ApiOperation(value = "删除收货地址")
    public Res<Integer> deleteAddress(@PathVariable("addressId") Long addressId) {
        int count = userAddressService.delete(addressId);
        return Res.ok(count);
    }

}
