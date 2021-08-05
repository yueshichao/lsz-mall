package com.lsz.mall.portal.entity;

import com.lsz.mall.base.entity.ProductCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 首页分类数据VO
 */
@Data
public class FirstCategoryVO {

    @ApiModelProperty("当前一级分类id")
    private Long categoryId;

    @ApiModelProperty("当前分类级别")
    private Integer categoryLevel;

    @ApiModelProperty("当前一级分类名称")
    private String categoryName;

    @ApiModelProperty("二级分类列表")
    private List<SecondLevelCategoryVO> secondLevelCategoryVOS;

    public FirstCategoryVO(ProductCategory productCategory) {
        this.categoryId = productCategory.getId();
        this.categoryLevel = productCategory.getLevel();
        this.categoryName = productCategory.getName();
    }


}
