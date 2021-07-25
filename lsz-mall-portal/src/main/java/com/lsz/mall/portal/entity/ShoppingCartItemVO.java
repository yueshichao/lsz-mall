package com.lsz.mall.portal.entity;

import com.lsz.mall.base.entity.CartItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ShoppingCartItemVO {

    @ApiModelProperty("购物项id")
    private Long cartItemId;

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

    public ShoppingCartItemVO(CartItem cartItem) {
        cartItemId = cartItem.getId();
        goodsId = cartItem.getProductId();
        goodsName = cartItem.getProductName();
        goodsCount = cartItem.getQuantity();
        goodsCoverImg = cartItem.getProductPic();
        sellingPrice = cartItem.getPrice().toPlainString();
    }

}
