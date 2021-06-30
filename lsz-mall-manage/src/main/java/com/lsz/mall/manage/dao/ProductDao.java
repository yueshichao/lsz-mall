package com.lsz.mall.manage.dao;

import com.lsz.mall.base.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ProductDao {

    @Select("SELECT * FROM product WHERE id = #{id}")
    Product findById(@Param("id") long id);

}
