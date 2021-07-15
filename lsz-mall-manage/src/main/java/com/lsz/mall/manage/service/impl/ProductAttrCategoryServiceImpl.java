package com.lsz.mall.manage.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsz.mall.base.entity.ProductAttributeCategory;
import com.lsz.mall.base.entity.ProductAttributeCategoryItem;
import com.lsz.mall.base.vo.CommonPage;
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
        ProductAttributeCategory productAttributeCategory = new ProductAttributeCategory(id, name);
        return productAttrDao.updateById(productAttributeCategory);
    }

    @Override
    public int delete(Long id) {
        return productAttrDao.deleteById(id);
    }

    @Override
    public ProductAttributeCategory getItem(Long id) {
        return productAttrDao.selectById(id);
    }

    @Override
    public CommonPage<ProductAttributeCategory> getPage(Integer pageSize, Integer pageNum) {
        IPage<ProductAttributeCategory> page = productAttrDao.selectPage(new Page<>(pageNum, pageSize), null);
        return CommonPage.restPage(page);
    }

    @Override
    public List<ProductAttributeCategoryItem> getListWithAttr() {
        return productAttrDao.getListWithAttr();
    }
}
