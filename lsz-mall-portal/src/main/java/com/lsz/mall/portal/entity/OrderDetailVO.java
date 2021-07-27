package com.lsz.mall.portal.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lsz.mall.base.entity.Order;
import com.lsz.mall.base.entity.OrderItem;
import com.lsz.mall.base.entity.OrderStatusEnum;
import com.lsz.mall.base.entity.PayTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Data
@NoArgsConstructor
public class OrderDetailVO {

    @ApiModelProperty("订单号")
    private String orderNo;

    @ApiModelProperty("订单价格")
    private String totalPrice;

    @ApiModelProperty("订单支付状态码")
    private Integer payStatus;

    @ApiModelProperty("订单支付方式")
    private Integer payType;

    @ApiModelProperty("订单支付方式")
    private String payTypeString;

    @ApiModelProperty("订单支付时间-")
    private Date payTime;

    @ApiModelProperty("订单状态码")
    private Integer orderStatus;

    @ApiModelProperty("订单状态")
    private String orderStatusString;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelProperty("订单项列表")
    private List<OrderItemVO> orderItems;

    public OrderDetailVO(Order order, List<OrderItem> orderItems) {
        this.orderNo = order.getOrderSn();
        this.totalPrice = order.getPayAmount().toPlainString();
        this.payStatus = order.getStatus();
        this.payType = order.getPayType();
        this.payTypeString = PayTypeEnum.code2String(order.getPayType());
        this.payTime = order.getPaymentTime();
        this.orderStatus = order.getStatus();
        this.orderStatusString = OrderStatusEnum.code2String(order.getStatus());
        this.createTime = order.getCreateTime();
        this.orderItems = orderItems
                .stream()
                .map(o -> new OrderItemVO(o))
                .collect(Collectors.toList());

    }


}
