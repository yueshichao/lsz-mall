package com.lsz.mall.manage.service;

import com.lsz.mall.base.entity.ProductAttrInfo;
import com.lsz.mall.base.entity.ProductAttribute;
import com.lsz.mall.base.entity.ProductAttributeParam;
import com.lsz.mall.base.vo.CommonPage;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductAttrService {

    /**
     * 根据分类分页获取商品属性
     *
     * @param cid  分类id
     * @param type 0->规格；1->参数
     */
    CommonPage<ProductAttribute> getPage(Long cid, Integer type, Integer pageSize, Integer pageNum);

    /**
     * 添加商品分类下的属性
     */
    @Transactional
    int create(ProductAttributeParam pmsProductAttributeParam);

    /**
     * 修改商品属性
     */
    int update(Long id, ProductAttributeParam productAttributeParam);

    /**
     * 获取单个商品属性信息
     */
    ProductAttribute getItem(Long id);

    /**
     * 批量删除商品属性
     */
    @Transactional
    int delete(List<Long> ids);

    /**
     * 获取商品分类对应属性列表
     */
    List<ProductAttrInfo> getProductAttrInfo(Long productCategoryId);

}
