package com.lsz.mall.manage.service;

import com.lsz.mall.base.entity.OrderSetting;

public interface OrderSettingService {

    OrderSetting getItem(Long id);

    int update(Long id, OrderSetting orderSetting);

}
