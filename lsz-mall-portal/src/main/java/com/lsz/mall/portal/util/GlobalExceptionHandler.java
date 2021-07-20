package com.lsz.mall.portal.util;

import com.lsz.mall.base.entity.Res;
import com.lsz.mall.base.entity.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * http请求的方法不正确
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Res serviceExceptionHandler(ServiceException e) {
        return Res.error(e.getMessage(), e.getErrorCode());
    }


}
