package com.lsz.mall.manage.service.impl;

import com.lsz.mall.base.entity.ProductAttribute;
import com.lsz.mall.base.entity.ProductAttributeCategory;
import com.lsz.mall.base.entity.ProductAttributeParam;
import com.lsz.mall.manage.dao.ProductAttrCategoryDao;
import com.lsz.mall.manage.dao.ProductAttrDao;
import com.lsz.mall.manage.service.ProductAttrService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductAttrServiceImpl implements ProductAttrService {

    @Autowired
    ProductAttrDao productAttrDao;

    @Autowired
    ProductAttrCategoryDao productAttrCategoryDao;

    @Override
    public List<ProductAttribute> getList(Long cid, Integer type, Integer pageSize, Integer pageNum) {
        return null;
    }

    @Override
    public int create(ProductAttributeParam productAttributeParam) {

        ProductAttribute productAttribute = new ProductAttribute();
        BeanUtils.copyProperties(productAttributeParam, productAttribute);
        int count = productAttrDao.insertSelective(productAttribute);

        ProductAttributeCategory productAttributeCategory = productAttrCategoryDao.selectByPrimaryKey(productAttribute.getProductAttributeCategoryId());
        if (productAttribute.getType() == 0) {
            productAttributeCategory.setAttributeCount(productAttributeCategory.getAttributeCount() + 1);
        } else if (productAttribute.getType() == 1) {
            productAttributeCategory.setParamCount(productAttributeCategory.getParamCount() + 1);
        }
        productAttrCategoryDao.updateByPrimaryKey(productAttributeCategory);

        return count;
    }

    @Override
    public int update(Long id, ProductAttributeParam productAttributeParam) {
        return 0;
    }

    @Override
    public ProductAttribute getItem(Long id) {
        return null;
    }

    @Override
    public int delete(List<Long> ids) {
        return 0;
    }
}
