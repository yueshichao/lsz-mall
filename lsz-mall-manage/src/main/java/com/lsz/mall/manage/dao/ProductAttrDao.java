package com.lsz.mall.manage.dao;

import com.lsz.mall.base.entity.PmsProductAttributeCategory;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface ProductAttrDao {

    int insertSelective(PmsProductAttributeCategory productAttributeCategory);

}
