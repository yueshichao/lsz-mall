package com.lsz.mall.base.entity;

import lombok.Getter;

public enum PayTypeEnum {

    UN_PAY(0, "未支付"),
    ZFB(1, "支付宝"),
    WX(2, "微信"),
    ;

    @Getter
    private int code;
    @Getter
    private String cn;

    PayTypeEnum(int code, String cn) {
        this.code = code;
        this.cn = cn;
    }

    public static String code2String(int code) {
        PayTypeEnum[] values = values();
        for (PayTypeEnum value : values) {
            if (value.code == code) {
                return value.getCn();
            }
        }
        return "未知状态";
    }


}