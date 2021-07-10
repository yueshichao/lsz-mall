package com.lsz.mall.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@TableName("pms_product_category_attribute_relation")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryAttributeRelation {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long productCategoryId;

    private Long productAttributeId;

    public ProductCategoryAttributeRelation(Long productCategoryId, Long productAttributeId) {
        this.productCategoryId = productCategoryId;
        this.productAttributeId = productAttributeId;
    }
}