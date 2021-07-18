package com.lsz.mall.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 申请信息封装
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderReturnApplyResult extends OrderReturnApply {

    @ApiModelProperty(value = "公司收货地址")
    private CompanyAddress companyAddress;

}
