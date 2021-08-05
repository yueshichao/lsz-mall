package com.lsz.mall.portal.entity;

import com.lsz.mall.base.entity.ProductCategory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 首页分类数据VO(第二级)
 */
@Data
public class SecondLevelCategoryVO {

    @ApiModelProperty("当前二级分类id")
    private Long categoryId;

    @ApiModelProperty("父级分类id")
    private Long parentId;

    @ApiModelProperty("当前分类级别")
    private Integer categoryLevel;

    @ApiModelProperty("当前二级分类名称")
    private String categoryName;

    @ApiModelProperty("三级分类列表")
    private List<ThirdLevelCategoryVO> thirdLevelCategoryVOS;

    public SecondLevelCategoryVO(ProductCategory productCategory, Long parentId) {
        this.categoryId = productCategory.getId();
        this.categoryLevel = productCategory.getLevel();
        this.categoryName = productCategory.getName();
        this.parentId = parentId;
    }
}
