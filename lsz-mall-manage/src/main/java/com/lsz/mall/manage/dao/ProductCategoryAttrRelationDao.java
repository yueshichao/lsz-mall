package com.lsz.mall.manage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsz.mall.base.entity.ProductCategoryAttributeRelation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ProductCategoryAttrRelationDao extends BaseMapper<ProductCategoryAttributeRelation> {


}
