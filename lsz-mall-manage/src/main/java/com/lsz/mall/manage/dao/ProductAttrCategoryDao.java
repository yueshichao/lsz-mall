package com.lsz.mall.manage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsz.mall.base.entity.ProductAttributeCategory;
import com.lsz.mall.base.entity.ProductAttributeCategoryItem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductAttrCategoryDao extends BaseMapper<ProductAttributeCategory> {

    int insertSelective(ProductAttributeCategory productAttributeCategory);

    ProductAttributeCategory selectByPrimaryKey(Long productAttributeCategoryId);

    int updateByPrimaryKey(ProductAttributeCategory productAttributeCategory);

    /**
     * 获取包含属性的商品属性分类
     */
    List<ProductAttributeCategoryItem> getListWithAttr();

}
