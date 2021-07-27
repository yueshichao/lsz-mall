package com.lsz.mall.portal.entity;

import com.lsz.mall.base.entity.UserAddress;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AddressParam extends SaveAddressParam {

    @ApiModelProperty("地址id")
    private Long addressId;

    @ApiModelProperty("用户id")
    private Long userId;

    public AddressParam(UserAddress userAddress) {
        super(userAddress);
        this.addressId = userAddress.getId();
        this.userId = userAddress.getMemberId();
    }


}
