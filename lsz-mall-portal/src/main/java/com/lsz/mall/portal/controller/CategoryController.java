package com.lsz.mall.portal.controller;

import com.lsz.mall.base.entity.Res;
import com.lsz.mall.portal.entity.FirstCategoryVO;
import com.lsz.mall.portal.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@Api(tags = "分类页")
@RequestMapping("/api/v1")
@Slf4j
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    @GetMapping("/categories")
    @ApiOperation(value = "获取分类页数据")
    public Res<List<FirstCategoryVO>> getCategories() {
        List<FirstCategoryVO> res = categoryService.getCategories();
        return Res.ok(res);
    }

}
