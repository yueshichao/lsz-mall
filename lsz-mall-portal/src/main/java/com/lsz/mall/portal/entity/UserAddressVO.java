package com.lsz.mall.portal.entity;

import com.lsz.mall.base.entity.UserAddress;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class UserAddressVO extends SaveAddressParam {

    @ApiModelProperty("地址id")
    private Long addressId;

    @ApiModelProperty("用户id")
    private Long userId;

    public UserAddressVO(UserAddress userAddress) {
        this.addressId = userAddress.getId();
        this.userId = userAddress.getMemberId();
        this.userName = userAddress.getName();
        this.userPhone = userAddress.getPhoneNumber();
        this.defaultFlag = userAddress.getDefaultStatus();
        this.provinceName = userAddress.getProvince();
        this.cityName = userAddress.getCity();
        this.regionName = userAddress.getRegion();
        this.detailAddress = userAddress.getDetailAddress();
    }

}
