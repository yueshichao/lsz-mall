package com.lsz.mall.manage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsz.mall.base.entity.ProductCategory;
import com.lsz.mall.base.entity.ProductCategoryWithChildrenItem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface ProductCategoryDao extends BaseMapper<ProductCategory> {

    /**
     * 获取商品分类及其子分类
     */
    List<ProductCategoryWithChildrenItem> listWithChildren();

}
