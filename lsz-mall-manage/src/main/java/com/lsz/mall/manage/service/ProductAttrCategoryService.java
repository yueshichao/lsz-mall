package com.lsz.mall.manage.service;

import com.lsz.mall.base.entity.ProductAttributeCategory;
import com.lsz.mall.base.entity.ProductAttributeCategoryItem;
import com.lsz.mall.base.vo.CommonPage;

import java.util.List;

public interface ProductAttrCategoryService {

    /**
     * 创建属性分类
     */
    int create(String name);

    /**
     * 修改属性分类
     */
    int update(Long id, String name);

    /**
     * 删除属性分类
     */
    int delete(Long id);

    /**
     * 获取属性分类详情
     */
    ProductAttributeCategory getItem(Long id);

    /**
     * 分页查询属性分类
     */
    CommonPage<ProductAttributeCategory> getPage(Integer pageSize, Integer pageNum);

    /**
     * 获取包含属性的属性分类
     */
    List<ProductAttributeCategoryItem> getListWithAttr();

}
