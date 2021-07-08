package com.lsz.mall.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("pms_product_attribute_category")
public class ProductAttributeCategory {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String name;

    // 属性列表
    private Integer attributeCount;

    // 参数数量
    private Integer paramCount;

}