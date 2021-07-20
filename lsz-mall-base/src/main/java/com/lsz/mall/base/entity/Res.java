package com.lsz.mall.base.entity;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;

@Data
public class Res<T> {

    protected String message;

    protected T data;

    // 状态码
    protected int resultCode;

    protected Long timestamp;

    public Res() {
    }

    public static <T> Res<T> error() {
        return error(500, -1, null);
    }

    public static <T> Res<T> error(String message, String code) {
        int newCode = StrUtil.isNotBlank(code) ? Integer.parseInt(code) : -1;
        return error(500, newCode, message);
    }

    public static <T> Res<T> error(String message, int code) {
        return error(500, code, message);
    }

    public static <T> Res<T> error(String message) {
        return error(message, null);
    }

    public static <T> Res<T> error(int status, int code, String message, Object... args) {
        Res<T> msg = new Res<>();
        msg.message = StrUtil.format(message, args);
        msg.code(code);
        return msg.putTimeStamp();
    }

    public static <T> Res<T> ok() {
        return ok(null);
    }

    public static <T> Res<T> ok(T result) {
        return (new Res<T>()).result(result).putTimeStamp().code(200);
    }

    private Res<T> putTimeStamp() {
        this.timestamp = System.currentTimeMillis();
        return this;
    }

    public Res<T> result(T result) {
        this.data = result;
        return this;
    }


    public String toString() {
        return JSON.toJSONStringWithDateFormat(this, "yyyy-MM-dd HH:mm:ss", new SerializerFeature[0]);
//        return JSON.toJSONString(this);
    }

    public Res<T> code(int code) {
        this.resultCode = code;
        return this;
    }

}
