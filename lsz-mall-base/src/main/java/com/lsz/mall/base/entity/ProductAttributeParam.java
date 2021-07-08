package com.lsz.mall.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/*
{
    "id": 7,
    "productAttributeCategoryId": 1,
    "name": "颜色",
    "selectType": 2,
    "inputType": 1,
    "inputList": "黑色,红色,白色,粉色",
    "sort": 100,
    "filterType": 0,
    "searchType": 0,
    "relatedStatus": 0,
    "handAddStatus": 1,
    "type": 0
}
* */
@Data
public class ProductAttributeParam {

    @NotEmpty
    @ApiModelProperty("属性分类ID")
    protected Long productAttributeCategoryId;

    @NotEmpty
    @ApiModelProperty("属性名称")
    protected String name;

    @ApiModelProperty("属性选择类型：0->唯一；1->单选；2->多选")
    protected Integer selectType;

    @ApiModelProperty("属性录入方式：0->手工录入；1->从列表中选取")
    protected Integer inputType;

    @ApiModelProperty("可选值列表，以逗号隔开")
    protected String inputList;

    protected Integer sort;

    @ApiModelProperty("分类筛选样式：0->普通；1->颜色")
    protected Integer filterType;

    @ApiModelProperty("检索类型；0->不需要进行检索；1->关键字检索；2->范围检索")
    protected Integer searchType;

    @ApiModelProperty("相同属性产品是否关联；0->不关联；1->关联")
    protected Integer relatedStatus;

    @ApiModelProperty("是否支持手动新增；0->不支持；1->支持")
    protected Integer handAddStatus;

    @ApiModelProperty("属性的类型；0->规格；1->参数")
    protected Integer type;

}
