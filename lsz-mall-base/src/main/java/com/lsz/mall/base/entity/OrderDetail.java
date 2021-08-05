package com.lsz.mall.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 订单详情信息
 */
@Data
public class OrderDetail extends Order {

    @ApiModelProperty("订单商品列表")
    private List<OrderItem> orderItemList;

    @ApiModelProperty("订单操作记录列表")
    private List<OrderOperateHistory> historyList;
}
