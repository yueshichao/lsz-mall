package com.lsz.mall.base.entity;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;

@Data
public class ResponseMessage<T> {

    protected String message;

    protected T result;

    protected int status;

    protected String code;

    protected Long timestamp;

    public ResponseMessage() {
    }

    public static <T> ResponseMessage<T> error() {
        return error(500, "-1", null);
    }

    public static <T> ResponseMessage<T> error(String message, String code) {
        return error(500, StrUtil.isNotBlank(code) ? code : "-1", message);
    }

    public static <T> ResponseMessage<T> error(String message) {
        return error(message, null);
    }

    public static <T> ResponseMessage<T> error(int status, String code, String message, Object... args) {
        ResponseMessage<T> msg = new ResponseMessage<>();
        msg.message = StrUtil.format(message, args);
        msg.status(status);
        msg.code(code);
        return msg.putTimeStamp();
    }

    public static <T> ResponseMessage<T> ok() {
        return ok(null);
    }

    public static <T> ResponseMessage<T> ok(T result) {
        return (new ResponseMessage<T>()).result(result).putTimeStamp().code("0").status(200);
    }

    private ResponseMessage<T> putTimeStamp() {
        this.timestamp = System.currentTimeMillis();
        return this;
    }

    public ResponseMessage<T> result(T result) {
        this.result = result;
        return this;
    }


    public String toString() {
        return JSON.toJSONStringWithDateFormat(this, "yyyy-MM-dd HH:mm:ss", new SerializerFeature[0]);
//        return JSON.toJSONString(this);
    }

    public ResponseMessage<T> status(int status) {
        this.status = status;
        return this;
    }

    public ResponseMessage<T> code(String code) {
        this.code = code;
        return this;
    }

}
