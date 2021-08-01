package com.lsz.mall.portal.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.ToStringSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.lsz.mall.base.entity.Order;
import com.lsz.mall.base.entity.OrderStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@Data
@NoArgsConstructor
public class OrderListVO {

    @JSONField(serializeUsing = ToStringSerializer.class)
    private Long orderId;

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("订单价格")
    private String totalPrice;

    @ApiModelProperty("订单支付方式")
    private Integer payType;

    @ApiModelProperty("订单状态码")
    private Integer orderStatus;

    @ApiModelProperty("订单状态")
    private String orderStatusString;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("订单项列表")
    private List<OrderItemVO> orderItems;

    public OrderListVO(Order order) {
        this.orderId = order.getId();
        this.orderNo = order.getOrderSn();
        this.totalPrice = order.getPayAmount().toPlainString();
        this.payType = order.getPayType();
        this.orderStatus = order.getStatus();
        this.orderStatusString = OrderStatusEnum.code2String(order.getStatus());
        this.createTime = order.getCreateTime();


    }
}
