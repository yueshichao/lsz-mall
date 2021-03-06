package com.lsz.mall.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


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
    "cateParentId": 2
}
* */
@TableName("pms_product")
@Data
public class Product {

    @TableId(type = IdType.AUTO)
    protected Long id;

    protected Long brandId;

    protected Long productCategoryId;

    protected Long feightTemplateId;

    protected Long productAttributeCategoryId;

    protected String name;

    protected String pic;

    @ApiModelProperty(value = "货号")
    protected String productSn;

    @ApiModelProperty(value = "删除状态：0->未删除；1->已删除")
    protected Integer deleteStatus;

    @ApiModelProperty(value = "上架状态：0->下架；1->上架")
    protected Integer publishStatus;

    @ApiModelProperty(value = "新品状态:0->不是新品；1->新品")
    protected Integer newStatus;

    @ApiModelProperty(value = "推荐状态；0->不推荐；1->推荐")
    protected Integer recommandStatus;

    @ApiModelProperty(value = "审核状态：0->未审核；1->审核通过")
    protected Integer verifyStatus;

    @ApiModelProperty(value = "排序")
    protected Integer sort;

    @ApiModelProperty(value = "销量")
    protected Integer sale;

    protected BigDecimal price;

    @ApiModelProperty(value = "促销价格")
    protected BigDecimal promotionPrice;

    @ApiModelProperty(value = "赠送的成长值")
    protected Integer giftGrowth;

    @ApiModelProperty(value = "赠送的积分")
    protected Integer giftPoint;

    @ApiModelProperty(value = "限制使用的积分数")
    protected Integer usePointLimit;

    @ApiModelProperty(value = "副标题")
    protected String subTitle;

    @ApiModelProperty(value = "市场价")
    protected BigDecimal originalPrice;

    @ApiModelProperty(value = "库存")
    protected Integer stock;

    @ApiModelProperty(value = "库存预警值")
    protected Integer lowStock;

    @ApiModelProperty(value = "单位")
    protected String unit;

    @ApiModelProperty(value = "商品重量，默认为克")
    protected BigDecimal weight;

    @ApiModelProperty(value = "是否为预告商品：0->不是；1->是")
    protected Integer previewStatus;

    @ApiModelProperty(value = "以逗号分割的产品服务：1->无忧退货；2->快速退款；3->免费包邮")
    protected String serviceIds;

    protected String keywords;

    protected String note;

    @ApiModelProperty(value = "画册图片，连产品图片限制为5张，以逗号分割")
    protected String albumPics;

    protected String detailTitle;

    @ApiModelProperty(value = "促销开始时间")
    protected Date promotionStartTime;

    @ApiModelProperty(value = "促销结束时间")
    protected Date promotionEndTime;

    @ApiModelProperty(value = "活动限购数量")
    protected Integer promotionPerLimit;

    @ApiModelProperty(value = "促销类型：0->没有促销使用原价;1->使用促销价；2->使用会员价；3->使用阶梯价格；4->使用满减价格；5->限时购")
    protected Integer promotionType;

    @ApiModelProperty(value = "品牌名称")
    protected String brandName;

    @ApiModelProperty(value = "商品分类名称")
    protected String productCategoryName;

    @ApiModelProperty(value = "商品描述")
    protected String description;

    protected String detailDesc;

    @ApiModelProperty(value = "产品详情网页内容")
    protected String detailHtml;

    @ApiModelProperty(value = "移动端网页详情")
    protected String detailMobileHtml;

}
