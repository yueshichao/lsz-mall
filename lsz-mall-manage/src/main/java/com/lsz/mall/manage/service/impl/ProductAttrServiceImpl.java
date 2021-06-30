package com.lsz.mall.manage.service.impl;

import com.lsz.mall.base.entity.PmsProductAttributeCategory;
import com.lsz.mall.manage.dao.ProductAttrDao;
import com.lsz.mall.manage.service.ProductAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAttrServiceImpl implements ProductAttrService {

    @Autowired
    ProductAttrDao productAttrDao;

    @Override
    public int create(String name) {
        PmsProductAttributeCategory productAttributeCategory = new PmsProductAttributeCategory();
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
    public PmsProductAttributeCategory getItem(Long id) {
        return null;
    }

    @Override
    public List<PmsProductAttributeCategory> getList(Integer pageSize, Integer pageNum) {
        return null;
    }
}
