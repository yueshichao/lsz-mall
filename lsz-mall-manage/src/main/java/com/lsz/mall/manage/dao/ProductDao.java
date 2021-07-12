package com.lsz.mall.manage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsz.mall.base.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ProductDao extends BaseMapper<Product> {

}
