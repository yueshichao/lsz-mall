package com.lsz.mall.manage.service;

import com.lsz.mall.base.entity.Product;
import com.lsz.mall.base.entity.ProductParam;
import com.lsz.mall.base.entity.ProductQueryParam;
import com.lsz.mall.base.vo.CommonPage;

public interface ProductService {

    int create(ProductParam productParam);

    CommonPage<Product> list(ProductQueryParam productQueryParam, Integer pageSize, Integer pageNum);

}
