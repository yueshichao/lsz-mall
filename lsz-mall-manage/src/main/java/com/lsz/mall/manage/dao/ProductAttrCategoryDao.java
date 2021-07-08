package com.lsz.mall.manage.dao;

import com.lsz.mall.base.entity.ProductAttributeCategory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ProductAttrCategoryDao {

    int insertSelective(ProductAttributeCategory productAttributeCategory);

    ProductAttributeCategory selectByPrimaryKey(Long productAttributeCategoryId);

    int updateByPrimaryKey(ProductAttributeCategory productAttributeCategory);
}
