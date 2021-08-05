package com.lsz.mall.manage.service.impl;

import com.lsz.mall.base.entity.OrderSetting;
import com.lsz.mall.manage.dao.OrderSettingDao;
import com.lsz.mall.manage.service.OrderSettingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderSettingServiceImpl implements OrderSettingService {

    @Autowired
    OrderSettingDao orderSettingDao;

    @Override
    public OrderSetting getItem(Long id) {
        return orderSettingDao.selectById(id);
    }

    @Override
    public int update(Long id, OrderSetting orderSetting) {
        orderSetting.setId(id);
        return orderSettingDao.updateById(orderSetting);
    }

}
