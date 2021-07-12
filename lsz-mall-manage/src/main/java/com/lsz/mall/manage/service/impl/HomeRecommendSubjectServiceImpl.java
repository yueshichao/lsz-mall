package com.lsz.mall.manage.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsz.mall.base.entity.HomeRecommendSubject;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.manage.dao.HomeRecommendSubjectDao;
import com.lsz.mall.manage.service.HomeRecommendSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeRecommendSubjectServiceImpl implements HomeRecommendSubjectService {

    @Autowired
    HomeRecommendSubjectDao homeRecommendSubjectDao;

    @Override
    public int create(List<HomeRecommendSubject> homeRecommendSubjectList) {

        int count = homeRecommendSubjectList.stream()
                .map(homeRecommendSubject -> {
                    homeRecommendSubject.setRecommendStatus(1);
                    homeRecommendSubject.setSort(0);
                    return homeRecommendSubject;
                })
                .map(homeRecommendSubject -> homeRecommendSubjectDao.insert(homeRecommendSubject))
                .mapToInt(i -> i)
                .sum();

        return count;
    }

    @Override
    public CommonPage<HomeRecommendSubject> getPage(String subjectName, Integer recommendStatus, Integer pageSize, Integer pageNum) {
        QueryWrapper<HomeRecommendSubject> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(recommendStatus != null, HomeRecommendSubject::getRecommendStatus, recommendStatus)
                .like(StrUtil.isNotBlank(subjectName), HomeRecommendSubject::getSubjectName, subjectName);
        IPage<HomeRecommendSubject> page = homeRecommendSubjectDao.selectPage(new Page<>(pageNum, pageSize), queryWrapper);

        return CommonPage.restPage(page);
    }
}
