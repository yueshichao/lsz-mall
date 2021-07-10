package com.lsz.mall.manage.service.impl;

import com.lsz.mall.base.entity.ProductAttributeCategory;
import com.lsz.mall.manage.dao.ProductAttrCategoryDao;
import com.lsz.mall.manage.service.ProductAttrCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAttrCategoryServiceImpl implements ProductAttrCategoryService {

    @Autowired
    ProductAttrCategoryDao productAttrDao;

    @Override
    public int create(String name) {
        ProductAttributeCategory productAttributeCategory = new ProductAttributeCategory();
        productAttributeCategory.setName(name);
        return productAttrDao.insertSelective(productAttributeCategory);
    }

    @Override
    public int update(Long id, String name) {
        return 0;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public ProductAttributeCategory getItem(Long id) {
        return null;
    }

    @Override
    public List<ProductAttributeCategory> getList(Integer pageSize, Integer pageNum) {
        return null;
    }
}
