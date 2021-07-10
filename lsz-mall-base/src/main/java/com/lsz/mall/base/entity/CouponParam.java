package com.lsz.mall.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/*
{
    "id": 19,
    "type": 0,
    "name": "手机分类专用",
    "platform": 0,
    "count": 100,
    "amount": 100,
    "perLimit": 1,
    "minPoint": 1000,
    "startTime": "2018-11-08T16:00:00.000+00:00",
    "endTime": "2018-11-16T16:00:00.000+00:00",
    "useType": 1,
    "note": "手机分类专用",
    "publishCount": 100,
    "useCount": 0,
    "receiveCount": 0,
    "enableTime": null,
    "code": null,
    "memberLevel": null,
    "productRelationList": [],
    "productCategoryRelationList": [
        {
            "id": 4,
            "couponId": null,
            "productCategoryId": 30,
            "productCategoryName": "手机配件",
            "parentCategoryName": "手机数码"
        }
    ]
}
* */
@Data
public class CouponParam extends Coupon {

    @ApiModelProperty("优惠券绑定的商品")
    private List<CouponProductRelation> productRelationList;

    @ApiModelProperty("优惠券绑定的商品分类")
    private List<CouponProductCategoryRelation> productCategoryRelationList;

}
