package com.lsz.mall.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
public class ProductCategoryBase {

    @ApiModelProperty(value = "上机分类的编号：0表示一级分类")
    protected Long parentId;

    @NotEmpty
    @ApiModelProperty(value = "商品分类名称", required = true)
    protected String name;

    @ApiModelProperty("分类单位")
    protected String productUnit;

    @ApiModelProperty(value = "是否显示在导航栏：0->不显示；1->显示")
    protected Integer navStatus;

    @ApiModelProperty(value = "显示状态：0->不显示；1->显示")
    protected Integer showStatus;

    @Min(value = 0)
    @ApiModelProperty("排序")
    protected Integer sort;

    @ApiModelProperty(value = "图标")
    protected String icon;

    @ApiModelProperty("关键字")
    protected String keywords;

    @ApiModelProperty(value = "描述")
    protected String description;

}