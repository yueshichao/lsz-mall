package com.lsz.mall.manage.service;

import com.lsz.mall.base.entity.Product;
import com.lsz.mall.base.entity.ProductParam;
import com.lsz.mall.base.entity.ProductQueryParam;
import com.lsz.mall.base.entity.ProductResult;
import com.lsz.mall.base.vo.CommonPage;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductService {

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    int create(ProductParam productParam);

    CommonPage<Product> getPage(ProductQueryParam productQueryParam, Integer pageSize, Integer pageNum);

    ProductResult getUpdateInfo(Long id);

    @Transactional
    int update(Long id, ProductParam productParam);

    List<Product> list(String keyword);

    @Transactional
    int updateVerifyStatus(List<Long> ids, Integer verifyStatus, String detail);

    int updatePublishStatus(List<Long> ids, Integer publishStatus);

    int updateRecommendStatus(List<Long> ids, Integer recommendStatus);

    int updateNewStatus(List<Long> ids, Integer newStatus);

    int updateDeleteStatus(List<Long> ids, Integer deleteStatus);

}
