package com.lsz.mall.portal.entity;

import com.lsz.mall.base.entity.OrderItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单详情页页面订单项VO
 */
@Data
@NoArgsConstructor
public class OrderItemVO {

    @ApiModelProperty("商品id")
    private Long goodsId;

    @ApiModelProperty("商品数量")
    private Integer goodsCount;

    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty("商品图片")
    private String goodsCoverImg;

    @ApiModelProperty("商品价格")
    private String sellingPrice;

    public OrderItemVO(OrderItem orderItem) {
        this.goodsId = orderItem.getProductId();
        this.goodsCount = orderItem.getProductQuantity();
        this.goodsName = orderItem.getProductName();
        this.goodsCoverImg = orderItem.getProductPic();
        this.sellingPrice = orderItem.getProductPrice().toPlainString();
    }

}
