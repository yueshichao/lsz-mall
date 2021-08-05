package com.lsz.mall.manage.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsz.mall.base.entity.OrderReturnReason;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.manage.dao.OrderReturnReasonDao;
import com.lsz.mall.manage.service.OrderReturnReasonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class OrderReturnReasonServiceImpl implements OrderReturnReasonService {

    @Autowired
    OrderReturnReasonDao orderReturnReasonDao;

    @Override
    public int create(OrderReturnReason returnReason) {
        returnReason.setCreateTime(new Date());
        return orderReturnReasonDao.insert(returnReason);
    }

    @Override
    public int update(Long id, OrderReturnReason returnReason) {
        returnReason.setId(id);
        return orderReturnReasonDao.updateById(returnReason);
    }

    @Override
    public int delete(List<Long> ids) {
        return orderReturnReasonDao.deleteBatchIds(ids);
    }

    @Override
    public CommonPage<OrderReturnReason> getPage(Integer pageSize, Integer pageNum) {
        IPage<OrderReturnReason> page = orderReturnReasonDao.selectPage(new Page<>(pageNum, pageSize), null);
        return CommonPage.restPage(page);
    }

    @Override
    public OrderReturnReason getItem(Long id) {
        return orderReturnReasonDao.selectById(id);
    }

    @Override
    public int updateStatus(List<Long> ids, Integer status) {
        if (!status.equals(0) && !status.equals(1)) {
            return 0;
        }
        return orderReturnReasonDao.updateStatusByIds(status, ids);
    }
}
