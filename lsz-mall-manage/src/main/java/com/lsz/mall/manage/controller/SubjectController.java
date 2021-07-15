package com.lsz.mall.manage.controller;

import com.lsz.mall.base.entity.ProductSubject;
import com.lsz.mall.base.entity.ResponseMessage;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.manage.service.ProductSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Api(description = "商品专题管理")
@RequestMapping("/subject")
public class SubjectController {
    @Autowired
    private ProductSubjectService subjectService;

    @ApiOperation("获取全部商品专题")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage<List<ProductSubject>> listAll() {
        List<ProductSubject> subjectList = subjectService.listAll();
        return ResponseMessage.ok(subjectList);
    }

    @ApiOperation(value = "根据专题名称分页获取专题")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage<CommonPage<ProductSubject>> getList(@RequestParam(value = "keyword", required = false) String keyword,
                                                               @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                               @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize) {
        CommonPage<ProductSubject> subjectList = subjectService.getPage(keyword, pageNum, pageSize);
        return ResponseMessage.ok(subjectList);
    }
}
