package com.lsz.mall.manage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsz.mall.base.entity.Product;
import com.lsz.mall.base.entity.ProductResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ProductDao extends BaseMapper<Product> {

    /**
     * 获取商品编辑信息
     */
    ProductResult getUpdateInfo(@Param("id") Long id);

}
