package com.lsz.mall.manage.controller;

import com.lsz.mall.base.entity.MemberLevel;
import com.lsz.mall.base.entity.ResponseMessage;
import com.lsz.mall.manage.service.MemberLevelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Api(tags = "UmsMemberLevelController", description = "会员等级管理")
@RequestMapping("/memberLevel")
public class MemberLevelController {
    @Autowired
    private MemberLevelService memberLevelService;

    @ApiOperation("查询所有会员等级")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage<List<MemberLevel>> list(@RequestParam("defaultStatus") Integer defaultStatus) {
        List<MemberLevel> memberLevelList = memberLevelService.list(defaultStatus);
        return ResponseMessage.ok(memberLevelList);
    }
}
