package com.lsz.mall.portal.util;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import com.lsz.mall.base.entity.ServiceException;
import com.lsz.mall.portal.annotation.PhoneCheck;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Slf4j
public class PhoneValidator implements ConstraintValidator<PhoneCheck, CharSequence> {


    @Override
    public void initialize(PhoneCheck constraintAnnotation) {
        log.debug("initialize");
    }

    @Override
    public boolean isValid(CharSequence mobileNum, ConstraintValidatorContext context) {
        boolean valid = !StrUtil.isEmpty(mobileNum) && Validator.isMobile(mobileNum);
        if (!valid) {
            throw new ServiceException("请输入中国地区手机号！");
        }
        return true;
    }

}
