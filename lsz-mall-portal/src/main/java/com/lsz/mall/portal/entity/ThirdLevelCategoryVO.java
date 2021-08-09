package com.lsz.mall.portal.entity;

import com.lsz.mall.base.entity.ProductCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 首页分类数据VO(第三级)
 */
@Data
@NoArgsConstructor
public class ThirdLevelCategoryVO {

    @ApiModelProperty("当前三级分类id")
    private Long categoryId;

    @ApiModelProperty("当前分类级别")
    private Integer categoryLevel;

    @ApiModelProperty("当前三级分类名称")
    private String categoryName;

    public ThirdLevelCategoryVO(ProductCategory productCategory) {
        this.categoryId = productCategory.getId();
        this.categoryLevel = productCategory.getLevel();
        this.categoryName = productCategory.getName();
    }
}
