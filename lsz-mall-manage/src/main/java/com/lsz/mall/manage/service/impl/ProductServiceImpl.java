package com.lsz.mall.manage.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsz.mall.base.entity.Product;
import com.lsz.mall.base.entity.ProductParam;
import com.lsz.mall.base.entity.ProductQueryParam;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.manage.dao.ProductDao;
import com.lsz.mall.manage.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Override
    public int create(ProductParam productParam) {
        Product product = new Product();
        BeanUtils.copyProperties(productParam, product);
        product.setId(null);
        int count = productDao.insert(product);

        return count;
    }

    @Override
    public CommonPage<Product> list(ProductQueryParam productQueryParam, Integer pageSize, Integer pageNum) {

        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(productQueryParam.getPublishStatus() != null, Product::getPublishStatus, productQueryParam.getPublishStatus())
                .eq(productQueryParam.getVerifyStatus() != null, Product::getVerifyStatus, productQueryParam.getVerifyStatus())
                .eq(productQueryParam.getBrandId() != null, Product::getBrandId, productQueryParam.getBrandId())
                .eq(productQueryParam.getProductCategoryId() != null, Product::getProductCategoryId, productQueryParam.getProductCategoryId())
                .eq(StrUtil.isNotBlank(productQueryParam.getProductSn()), Product::getProductSn, productQueryParam.getProductSn())
                .like(StrUtil.isNotBlank(productQueryParam.getKeyword()), Product::getName, productQueryParam.getKeyword());

        IPage<Product> page = productDao.selectPage(new Page<>(pageNum, pageSize), queryWrapper);

        return CommonPage.restPage(page);
    }


}
