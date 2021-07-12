package com.lsz.mall.manage.service;

import com.lsz.mall.base.entity.HomeRecommendSubject;
import com.lsz.mall.base.vo.CommonPage;

import java.util.List;

public interface HomeRecommendSubjectService {


    int create(List<HomeRecommendSubject> homeRecommendSubjectList);

    CommonPage<HomeRecommendSubject> getPage(String subjectName, Integer recommendStatus, Integer pageSize, Integer pageNum);

}
