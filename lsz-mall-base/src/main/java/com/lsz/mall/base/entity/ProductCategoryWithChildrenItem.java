package com.lsz.mall.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ProductCategoryWithChildrenItem extends ProductCategory {

    @ApiModelProperty("子级分类")
    private List<ProductCategoryWithChildrenItem> children;

    public ProductCategoryWithChildrenItem(List<ProductCategoryWithChildrenItem> children) {
        this.children = children;
    }

    public ProductCategoryWithChildrenItem(ProductCategory p) {
        this.id = p.id;
        this.name = p.name;
    }

    public String getValue() {
        return id + "";
    }

    public String getLabel() {
        return name;
    }

}
