package com.lsz.mall.manage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsz.mall.base.entity.ProductAttrInfo;
import com.lsz.mall.base.entity.ProductAttribute;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ProductAttrDao extends BaseMapper<ProductAttribute> {

    int insertSelective(ProductAttribute productAttribute);

    /**
     * 获取商品属性信息
     */
    List<ProductAttrInfo> getProductAttrInfo(@Param("id") Long productCategoryId);

}
