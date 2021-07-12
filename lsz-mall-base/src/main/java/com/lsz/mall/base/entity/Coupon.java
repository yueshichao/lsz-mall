package com.lsz.mall.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

/*
{
    "id": 2,
    "type": 0,
    "name": "全品类通用券",
    "platform": 0,
    "count": 92,
    "amount": 10,
    "perLimit": 1,
    "minPoint": 100,
    "startTime": "2018-08-27T08:40:47.000+00:00",
    "endTime": "2018-11-23T08:40:47.000+00:00",
    "useType": 0,
    "note": "满100减10",
    "publishCount": 100,
    "useCount": 0,
    "receiveCount": 8,
    "enableTime": "2018-08-27T08:40:47.000+00:00",
    "code": null,
    "memberLevel": null
}
* */
@Data
@TableName("sms_coupon")
public class Coupon {

    @TableId(type = IdType.AUTO)
    protected Long id;

    @ApiModelProperty(value = "优惠券类型；0->全场赠券；1->会员赠券；2->购物赠券；3->注册赠券")
    protected Integer type;

    protected String name;

    @ApiModelProperty(value = "使用平台：0->全部；1->移动；2->PC")
    protected Integer platform;

    @ApiModelProperty(value = "数量")
    protected Integer count;

    @ApiModelProperty(value = "金额")
    protected BigDecimal amount;

    @ApiModelProperty(value = "每人限领张数")
    protected Integer perLimit;

    @ApiModelProperty(value = "使用门槛；0表示无门槛")
    protected BigDecimal minPoint;

    protected Date startTime;

    protected Date endTime;

    @ApiModelProperty(value = "使用类型：0->全场通用；1->指定分类；2->指定商品")
    protected Integer useType;

    /*
    * 使用类型
    * */
    public enum UseType {
        USE_TYPE_ALL(0, "全场通用"),
        USE_TYPE_CATEGORY(1, "指定分类"),
        USE_TYPE_PRODUCT(2, "指定商品"),
        ;

        @Getter
        int value;

        @Getter
        String desc;

        UseType(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }
    }

    @ApiModelProperty(value = "备注")
    protected String note;

    @ApiModelProperty(value = "发行数量")
    protected Integer publishCount;

    @ApiModelProperty(value = "已使用数量")
    protected Integer useCount;

    @ApiModelProperty(value = "领取数量")
    protected Integer receiveCount;

    @ApiModelProperty(value = "可以领取的日期")
    protected Date enableTime;

    @ApiModelProperty(value = "优惠码")
    protected String code;

    @ApiModelProperty(value = "可领取的会员类型：0->无限时")
    protected Integer memberLevel;

}