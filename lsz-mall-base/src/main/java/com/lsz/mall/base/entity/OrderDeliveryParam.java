package com.lsz.mall.base.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单发货参数
 */
@Data
@NoArgsConstructor
public class OrderDeliveryParam {

    @ApiModelProperty("订单id")
    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long orderId;

    @ApiModelProperty("物流公司")
    private String deliveryCompany;

    @ApiModelProperty("物流单号")
    private String deliverySn;

}
