package com.lsz.mall.portal.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class UpdateCartItemParam {

    @ApiModelProperty("购物项id")
    private Long cartItemId;

    @ApiModelProperty("商品数量")
    private Integer goodsCount;

}
