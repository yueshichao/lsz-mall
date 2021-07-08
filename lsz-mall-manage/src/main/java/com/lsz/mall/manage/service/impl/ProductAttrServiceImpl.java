package com.lsz.mall.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    public IPage<ProductAttribute> getPage(Long cid, Integer type, Integer pageSize, Integer pageNum) {
        QueryWrapper<ProductAttribute> queryWrapper = new QueryWrapper<>();
        // 字段映射可行
//        queryWrapper.eq("product_attribute_category_id", cid);
        // 此种写法报错
//        queryWrapper.lambda().eq(p -> p.getProductAttributeCategoryId(), cid);
        // 双冒号不报错
        queryWrapper.lambda().eq(ProductAttributeParam::getProductAttributeCategoryId, cid);
        Page<ProductAttribute> p = new Page<>(pageNum, pageSize);
        IPage<ProductAttribute> page = productAttrDao.selectPage(p, queryWrapper);
//        List<ProductAttribute> list = productAttrDao.selectList(queryWrapper);
        return page;
    }

    @Override
    public int create(ProductAttributeParam productAttributeParam) {

        ProductAttribute productAttribute = new ProductAttribute();
        BeanUtils.copyProperties(productAttributeParam, productAttribute);
//        int count = productAttrDao.insertSelective(productAttribute);
        int count = productAttrDao.insert(productAttribute);

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
