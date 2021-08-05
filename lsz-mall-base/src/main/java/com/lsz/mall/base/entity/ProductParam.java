package com.lsz.mall.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/*
{
    "id": 26,
    "brandId": 3,
    "productCategoryId": 19,
    "feightTemplateId": 0,
    "productAttributeCategoryId": 3,
    "name": "华为 HUAWEI P20 ",
    "pic": "http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ac1bf58Ndefaac16.jpg",
    "productSn": "6946605",
    "deleteStatus": 0,
    "publishStatus": 1,
    "newStatus": 1,
    "recommandStatus": 1,
    "verifyStatus": 0,
    "sort": 100,
    "sale": 0,
    "price": 3788,
    "promotionPrice": null,
    "giftGrowth": 3788,
    "giftPoint": 3788,
    "usePointLimit": 0,
    "subTitle": "AI智慧全面屏 6GB +64GB 亮黑色 全网通版 移动联通电信4G手机 双卡双待手机 双卡双待",
    "originalPrice": 4288,
    "stock": 1000,
    "lowStock": 0,
    "unit": "件",
    "weight": 0,
    "previewStatus": 1,
    "serviceIds": "2,3,1",
    "keywords": "",
    "note": "",
    "albumPics": "http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ab46a3cN616bdc41.jpg,http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ac1bf5fN2522b9dc.jpg",
    "detailTitle": "",
    "promotionStartTime": null,
    "promotionEndTime": null,
    "promotionPerLimit": 0,
    "promotionType": 1,
    "brandName": "华为",
    "productCategoryName": "手机通讯",
    "description": "",
    "detailDesc": "",
    "detailHtml": "<p><img class=\"wscnph\" src=\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ad44f1cNf51f3bb0.jpg\" /><img class=\"wscnph\" src=\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ad44fa8Nfcf71c10.jpg\" /><img class=\"wscnph\" src=\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ad44fa9N40e78ee0.jpg\" /><img class=\"wscnph\" src=\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ad457f4N1c94bdda.jpg\" /><img class=\"wscnph\" src=\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5ad457f5Nd30de41d.jpg\" /><img class=\"wscnph\" src=\"http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180607/5b10fb0eN0eb053fb.jpg\" /></p>",
    "detailMobileHtml": "",
    "productLadderList": [
        {
            "id": 87,
            "productId": 26,
            "count": 0,
            "discount": 0,
            "price": 0
        }
    ],
    "productFullReductionList": [
        {
            "id": 82,
            "productId": 26,
            "fullPrice": 0,
            "reducePrice": 0
        }
    ],
    "memberPriceList": [
        {
            "id": 258,
            "productId": 26,
            "memberLevelId": 1,
            "memberPrice": null,
            "memberLevelName": "黄金会员"
        },
        {
            "id": 259,
            "productId": 26,
            "memberLevelId": 2,
            "memberPrice": null,
            "memberLevelName": "白金会员"
        },
        {
            "id": 260,
            "productId": 26,
            "memberLevelId": 3,
            "memberPrice": null,
            "memberLevelName": "钻石会员"
        }
    ],
    "skuStockList": [
        {
            "id": 110,
            "productId": 26,
            "skuCode": "201806070026001",
            "price": 3788,
            "stock": 499,
            "lowStock": null,
            "pic": null,
            "sale": null,
            "promotionPrice": null,
            "lockStock": null,
            "spData": "[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"16G\"}]"
        },
        {
            "id": 111,
            "productId": 26,
            "skuCode": "201806070026002",
            "price": 3999,
            "stock": 500,
            "lowStock": null,
            "pic": null,
            "sale": null,
            "promotionPrice": null,
            "lockStock": null,
            "spData": "[{\"key\":\"颜色\",\"value\":\"金色\"},{\"key\":\"容量\",\"value\":\"32G\"}]"
        },
        {
            "id": 112,
            "productId": 26,
            "skuCode": "201806070026003",
            "price": 3788,
            "stock": 500,
            "lowStock": null,
            "pic": null,
            "sale": null,
            "promotionPrice": null,
            "lockStock": null,
            "spData": "[{\"key\":\"颜色\",\"value\":\"银色\"},{\"key\":\"容量\",\"value\":\"16G\"}]"
        },
        {
            "id": 113,
            "productId": 26,
            "skuCode": "201806070026004",
            "price": 3999,
            "stock": 500,
            "lowStock": null,
            "pic": null,
            "sale": null,
            "promotionPrice": null,
            "lockStock": null,
            "spData": "[{\"key\":\"颜色\",\"value\":\"银色\"},{\"key\":\"容量\",\"value\":\"32G\"}]"
        }
    ],
    "productAttributeValueList": [
        {
            "productAttributeId": 43,
            "value": "金色,银色"
        },
        {
            "productAttributeId": 45,
            "value": "5.0"
        },
        {
            "productAttributeId": 46,
            "value": "4G"
        },
        {
            "productAttributeId": 47,
            "value": "Android"
        },
        {
            "productAttributeId": 48,
            "value": "3000"
        }
    ],
    "subjectProductRelationList": [
        {
            "id": 49,
            "subjectId": 2,
            "productId": 26
        },
        {
            "id": 50,
            "subjectId": 3,
            "productId": 26
        },
        {
            "id": 51,
            "subjectId": 6,
            "productId": 26
        }
    ],
    "prefrenceAreaProductRelationList": [],
    "cateParentId": 2
}
 * */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProductParam extends Product {

    @ApiModelProperty("商品阶梯价格设置")
    protected List<ProductLadder> productLadderList;

    @ApiModelProperty("商品满减价格设置")
    protected List<ProductFullReduction> productFullReductionList;

    @ApiModelProperty("商品会员价格设置")
    protected List<MemberPrice> memberPriceList;

    @ApiModelProperty("商品的sku库存信息")
    protected List<SkuStock> skuStockList;

    @ApiModelProperty("商品参数及自定义规格属性")
    protected List<ProductAttrValue> productAttributeValueList;

    @ApiModelProperty("专题和商品关系")
    protected List<SubjectProductRelation> subjectProductRelationList;

}
