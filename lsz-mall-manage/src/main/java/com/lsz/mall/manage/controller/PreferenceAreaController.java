package com.lsz.mall.manage.controller;

import com.lsz.mall.base.entity.PreferenceArea;
import com.lsz.mall.base.entity.ResponseMessage;
import com.lsz.mall.manage.service.PreferenceAreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Api(description = "商品优选管理")
@RequestMapping("/preferenceArea")
public class PreferenceAreaController {
    @Autowired
    private PreferenceAreaService preferenceAreaService;

    @ApiOperation("获取所有商品优选")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage<List<PreferenceArea>> listAll() {
        List<PreferenceArea> PreferenceAreaList = preferenceAreaService.listAll();
        return ResponseMessage.ok(PreferenceAreaList);
    }
}
