package com.lsz.mall.base.entity;

import lombok.Data;

@Data
public class ServiceException extends RuntimeException {

    private String errorCode;

    public ServiceException() {
        super("业务异常");
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
