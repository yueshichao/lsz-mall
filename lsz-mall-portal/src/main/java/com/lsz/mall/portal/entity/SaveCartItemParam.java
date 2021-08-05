package com.lsz.mall.portal.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class SaveCartItemParam {

    @ApiModelProperty("商品数量")
    private Integer productCount;

    @ApiModelProperty("商品id")
    @NotNull
    private Long productId;

    @NotNull
    private Long productSkuId;

}
