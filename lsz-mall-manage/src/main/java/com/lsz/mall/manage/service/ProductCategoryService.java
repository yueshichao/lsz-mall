package com.lsz.mall.manage.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lsz.mall.base.entity.*;
import com.lsz.mall.base.vo.CommonPage;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductCategoryService {
    /**
     * 创建商品分类
     */
    @Transactional
    int create(ProductCategoryParam ProductCategoryParam);

    /**
     * 修改商品分类
     */
    @Transactional
    int update(Long id, ProductCategoryParam ProductCategoryParam);

    /**
     * 分页获取商品分类
     */
    CommonPage<ProductCategory> getPage(Long parentId, Integer pageSize, Integer pageNum);

    /**
     * 删除商品分类
     */
    int delete(Long id);

    /**
     * 根据ID获取商品分类
     */
    ProductCategory getItem(Long id);

    /**
     * 批量修改导航状态
     */
    int updateNavStatus(List<Long> ids, Integer navStatus);

    /**
     * 批量修改显示状态
     */
    int updateShowStatus(List<Long> ids, Integer showStatus);

    /**
     * 以层级形式获取商品分类
     */
    List<ProductCategoryWithChildrenItem> listWithChildren();

}
