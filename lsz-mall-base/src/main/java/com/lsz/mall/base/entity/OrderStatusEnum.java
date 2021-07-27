package com.lsz.mall.base.entity;

import lombok.Getter;

public enum OrderStatusEnum {

    TO_PAY(0, "待付款"),
    TO_POST_PRODUCT(1, "待发货"),
    POSTED_PRODUCT(2, "已发货"),
    FINISHED(3, "已完成"),
    CLOSED(4, "已关闭"),
    INVALID(5, "无效订单"),
    ;

    @Getter
    private int code;
    @Getter
    private String cn;

    OrderStatusEnum(int code, String cn) {
        this.code = code;
        this.cn = cn;
    }

    public static String code2String(int code) {
        OrderStatusEnum[] values = values();
        for (OrderStatusEnum value : values) {
            if (value.code == code) {
                return value.getCn();
            }
        }
        return "未知状态";
    }


}