package com.lsz.mall.portal.controller;

import com.lsz.mall.base.entity.Res;
import com.lsz.mall.portal.entity.HomeInfoVO;
import com.lsz.mall.portal.service.HomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "首页")
@RequestMapping("/api/v1")
@Slf4j
public class HomeController {

    @Autowired
    HomeService homeService;

    @GetMapping("/index-infos")
    @ApiOperation(value = "获取首页数据", notes = "轮播图、新品、推荐等")
    public Res homeInfos() {
        HomeInfoVO homeInfoVO = homeService.homeInfos();
        return Res.ok(homeInfoVO);
    }

}
