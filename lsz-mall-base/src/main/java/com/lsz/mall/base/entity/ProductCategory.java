package com.lsz.mall.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@TableName("pms_product_category")
public class ProductCategory extends ProductCategoryBase {

    @TableId(type = IdType.AUTO)
    protected Long id;

    @ApiModelProperty(value = "分类级别：0->1级；1->2级")
    protected Integer level;

    protected Integer productCount;

    public ProductCategory(Long id) {
        this.id = id;
    }
}