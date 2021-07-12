package com.lsz.mall.manage.controller;

import com.lsz.mall.base.entity.HomeRecommendSubject;
import com.lsz.mall.base.entity.ResponseMessage;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.manage.service.HomeRecommendSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(description = "首页专题推荐管理")
@RequestMapping("/home/recommendSubject")
public class HomeRecommendSubjectController {

    @Autowired
    private HomeRecommendSubjectService recommendSubjectService;

    @ApiOperation("添加首页推荐专题")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage create(@RequestBody List<HomeRecommendSubject> homeRecommendSubjectList) {
        int count = recommendSubjectService.create(homeRecommendSubjectList);
        if (count > 0) {
            return ResponseMessage.ok(count);
        }
        return ResponseMessage.error();
    }

    @ApiOperation("分页查询推荐")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage<CommonPage<HomeRecommendSubject>> list(@RequestParam(value = "subjectName", required = false) String subjectName,
                                                                  @RequestParam(value = "recommendStatus", required = false) Integer recommendStatus,
                                                                  @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        CommonPage<HomeRecommendSubject> homeRecommendSubjectList = recommendSubjectService.getPage(subjectName, recommendStatus, pageSize, pageNum);
        return ResponseMessage.ok(homeRecommendSubjectList);
    }
}
