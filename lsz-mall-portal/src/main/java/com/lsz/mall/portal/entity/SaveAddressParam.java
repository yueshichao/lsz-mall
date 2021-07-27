package com.lsz.mall.portal.entity;

import com.lsz.mall.base.entity.UserAddress;
import com.lsz.mall.portal.annotation.PhoneCheck;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class SaveAddressParam {

    @ApiModelProperty("收件人名称")
    protected String userName;

    @ApiModelProperty("收件人联系方式")
    @PhoneCheck(message = "请输入中国地区的手机号！")
    protected String userPhone;

    @ApiModelProperty("是否默认地址 0-不是 1-是")
    protected Integer defaultFlag;

    @ApiModelProperty("省")
    protected String provinceName;

    @ApiModelProperty("市")
    protected String cityName;

    @ApiModelProperty("区/县")
    protected String regionName;

    @ApiModelProperty("详细地址")
    protected String detailAddress;

    public SaveAddressParam(UserAddress userAddress) {
        this.userName = userAddress.getName();
        this.userPhone = userAddress.getPhoneNumber();
        this.defaultFlag = userAddress.getDefaultStatus();
        this.provinceName = userAddress.getProvince();
        this.cityName = userAddress.getCity();
        this.regionName = userAddress.getRegion();
        this.detailAddress = userAddress.getDetailAddress();
    }

}
