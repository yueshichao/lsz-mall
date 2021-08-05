package com.lsz.mall.manage.controller;

import com.lsz.mall.base.entity.CompanyAddress;
import com.lsz.mall.base.entity.ResponseMessage;
import com.lsz.mall.manage.service.CompanyAddressService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Api(description = "收货地址管理")
@RequestMapping("/companyAddress")
public class CompanyAddressController {

    @Autowired
    private CompanyAddressService companyAddressService;

    @ApiOperation("获取所有收货地址")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage<List<CompanyAddress>> list() {
        List<CompanyAddress> companyAddressList = companyAddressService.list();
        return ResponseMessage.ok(companyAddressList);
    }
}
