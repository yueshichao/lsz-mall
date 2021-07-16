package com.lsz.mall.manage.service;

import com.lsz.mall.base.entity.ProductSubject;
import com.lsz.mall.base.vo.CommonPage;

import java.util.List;

public interface ProductSubjectService {

    List<ProductSubject> listAll();

    CommonPage<ProductSubject> getPage(String keyword, Integer pageNum, Integer pageSize);

}
