package com.lsz.mall.portal.entity;

import com.lsz.mall.base.entity.HomeAdvertise;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 首页轮播图VO
 */
@Data
@NoArgsConstructor
public class HomeCarouselPicVO {

    @ApiModelProperty("轮播图图片地址")
    private String carouselUrl;

    @ApiModelProperty("轮播图点击后的跳转路径")
    private String redirectUrl;

    public HomeCarouselPicVO(HomeAdvertise homeAdvertise) {
        this.carouselUrl = homeAdvertise.getUrl();
        this.redirectUrl = homeAdvertise.getUrl();
    }
}
