package com.lsz.mall.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
public class ProductAttributeCategoryItem extends ProductAttributeCategory {

    @ApiModelProperty(value = "商品属性列表")
    private List<ProductAttribute> productAttributeList;

}
