package com.lsz.mall.portal.service;

import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.portal.entity.OrderDetailVO;
import com.lsz.mall.portal.entity.OrderListVO;
import com.lsz.mall.portal.entity.SaveOrderParam;
import org.springframework.transaction.annotation.Transactional;


public interface OrderService {


    @Transactional
    String saveOrder(SaveOrderParam saveOrderParam);

    OrderDetailVO getDetail(String orderId);

    CommonPage<OrderListVO> getPage(Integer pageNo, Integer pageSize, Integer status);

    @Transactional
    int cancelOrder(String orderId);

    @Transactional
    int cancelOrder(String orderId, boolean auto);

    @Transactional
    int confirmReceive(String orderId);

    @Transactional
    int paySuccess(String orderId, int payType);

}
