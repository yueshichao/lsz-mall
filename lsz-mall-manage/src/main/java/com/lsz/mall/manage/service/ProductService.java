package com.lsz.mall.manage.service;

import com.lsz.mall.base.entity.Product;
import com.lsz.mall.base.entity.ProductParam;
import com.lsz.mall.base.entity.ProductQueryParam;
import com.lsz.mall.base.vo.CommonPage;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public interface ProductService {

    @Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
    int create(ProductParam productParam);

    CommonPage<Product> getPage(ProductQueryParam productQueryParam, Integer pageSize, Integer pageNum);

}
