package com.lsz.mall.portal.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HomeInfoVO implements Serializable {

    @ApiModelProperty("轮播图(列表)")
    private List<HomeCarouselPicVO> carousels;

    @ApiModelProperty("首页热销商品(列表)")
    private List<HomeConfigGoodsVO> hotGoodses;

    @ApiModelProperty("首页新品推荐(列表)")
    private List<HomeConfigGoodsVO> newGoodses;

    @ApiModelProperty("首页推荐商品(列表)")
    private List<HomeConfigGoodsVO> recommendGoodses;
}
