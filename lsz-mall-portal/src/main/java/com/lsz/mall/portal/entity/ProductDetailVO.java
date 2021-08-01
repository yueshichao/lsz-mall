package com.lsz.mall.portal.entity;

import com.lsz.mall.base.entity.Product;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
public class ProductDetailVO {

    @ApiModelProperty("商品id")
    private Long goodsId;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("商品简介")
    private String goodsIntro;

    @ApiModelProperty("商品图片地址")
    private String goodsCoverImg;

    @ApiModelProperty("商品价格")
    private String sellingPrice;

    @ApiModelProperty("商品标签")
    private String tag;

    @ApiModelProperty("商品图片")
    private String[] goodsCarouselList;

    @ApiModelProperty("商品原价")
    private String originalPrice;

    @ApiModelProperty("商品详情字段")
    private String goodsDetailContent;

    private List<SkuStockVO> skuStockList;

    public ProductDetailVO(Product product) {
        this.goodsId = product.getId();
        this.goodsName = product.getName();
        this.goodsIntro = product.getDescription();
        this.goodsCoverImg = product.getPic();
        this.sellingPrice = product.getPrice().toPlainString();
        this.tag = product.getProductCategoryName();
        this.goodsCarouselList = new String[]{product.getPic()};
        this.originalPrice = product.getPrice().toPlainString();
        this.goodsDetailContent = product.getDetailDesc();
    }

}
