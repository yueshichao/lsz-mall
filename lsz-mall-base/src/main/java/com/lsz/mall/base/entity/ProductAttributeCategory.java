package com.lsz.mall.base.entity;

import lombok.Data;

@Data
public class ProductAttributeCategory {

    private Long id;

    private String name;

    // 属性列表
    private Integer attributeCount;

    // 参数数量
    private Integer paramCount;

}