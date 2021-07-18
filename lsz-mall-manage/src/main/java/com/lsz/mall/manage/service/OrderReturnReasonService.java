package com.lsz.mall.manage.service;

import com.lsz.mall.base.entity.OrderReturnReason;
import com.lsz.mall.base.vo.CommonPage;

import java.util.List;

public interface OrderReturnReasonService {

    int create(OrderReturnReason returnReason);

    int update(Long id, OrderReturnReason returnReason);

    int delete(List<Long> ids);

    CommonPage<OrderReturnReason> getPage(Integer pageSize, Integer pageNum);

    OrderReturnReason getItem(Long id);

    int updateStatus(List<Long> ids, Integer status);

}
