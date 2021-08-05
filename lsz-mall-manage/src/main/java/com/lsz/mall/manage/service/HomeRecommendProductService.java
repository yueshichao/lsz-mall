package com.lsz.mall.manage.service;

import com.lsz.mall.base.entity.HomeRecommendProduct;
import com.lsz.mall.base.vo.CommonPage;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HomeRecommendProductService {

    @Transactional
    int create(List<HomeRecommendProduct> homeRecommendProductList);

    @Transactional
    int updateSort(Long id, Integer sort);

    @Transactional
    int delete(List<Long> ids);

    @Transactional
    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    CommonPage<HomeRecommendProduct> getPage(String productName, Integer recommendStatus, Integer pageSize, Integer pageNum);

}
