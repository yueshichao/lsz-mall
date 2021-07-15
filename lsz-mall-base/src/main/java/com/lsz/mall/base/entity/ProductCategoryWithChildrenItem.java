package com.lsz.mall.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

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
}
