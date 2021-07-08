package com.lsz.mall.manage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsz.mall.base.entity.ProductAttribute;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ProductAttrDao extends BaseMapper<ProductAttribute> {

    int insertSelective(ProductAttribute productAttribute);

}
