package com.lsz.mall.base.entity;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;


/*
{
    "id": 7,
    "parentId": 1,
    "name": "外套",
    "level": 1,
    "productCount": 100,
    "productUnit": "件",
    "navStatus": 1,
    "showStatus": 1,
    "sort": 0,
    "icon": "",
    "keywords": "外套",
    "description": "外套",
    "productAttributeIdList": []
}
* */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductCategoryParam extends ProductCategoryBase {

    @ApiModelProperty("产品相关筛选属性集合")
    private List<Long> productAttributeIdList;

}
