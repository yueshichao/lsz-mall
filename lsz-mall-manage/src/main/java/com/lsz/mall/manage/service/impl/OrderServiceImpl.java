package com.lsz.mall.manage.service.impl;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsz.mall.base.entity.*;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.manage.dao.OrderDao;
import com.lsz.mall.manage.dao.OrderOperateHistoryDao;
import com.lsz.mall.manage.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    OrderOperateHistoryDao orderOperateHistoryDao;

    @Override
    public CommonPage<Order> getPage(OrderQueryParam queryParam, Integer pageSize, Integer pageNum) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        // mybatis-plus嵌套查询：https://blog.csdn.net/weixin_45267102/article/details/109142346
        wrapper.eq(StrUtil.isNotBlank(queryParam.getOrderSn()), Order::getOrderSn, queryParam.getOrderSn())
                .eq(queryParam.getStatus() != null, Order::getStatus, queryParam.getStatus())
                .eq(queryParam.getSourceType() != null, Order::getSourceType, queryParam.getSourceType())
                .eq(queryParam.getOrderType() != null, Order::getOrderType, queryParam.getOrderType())
//                .eq(StrUtil.isNotBlank(queryParam.getCreateTime()), Order::getCreateTime, DateUtil.parse(queryParam.getCreateTime()).toJdkDate())
                .and(StrUtil.isNotBlank(queryParam.getReceiverKeyword()), w -> w
                        .like(StrUtil.isNotBlank(queryParam.getReceiverKeyword()), Order::getReceiverName, queryParam.getReceiverKeyword())
                        .or()
                        .like(StrUtil.isNotBlank(queryParam.getReceiverKeyword()), Order::getReceiverPhone, queryParam.getReceiverKeyword())
                );
        if (StrUtil.isNotBlank(queryParam.getCreateTime())) {
            Date startTime = DateUtil.parse(queryParam.getCreateTime()).toJdkDate();
            Date endTime = DateUtil.date(startTime).offset(DateField.DAY_OF_MONTH, 1).toJdkDate();
            wrapper.between(Order::getCreateTime, startTime, endTime);
        }

        IPage<Order> page = orderDao.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return CommonPage.restPage(page);
    }

    @Override
    public int delivery(List<OrderDeliveryParam> deliveryParamList) {
        int deliveryCount = orderDao.delivery(deliveryParamList);
        log.debug("deliveryCount = {}", deliveryCount);

        //添加操作记录
        int count = deliveryParamList.stream()
                .map(omsOrderDeliveryParam -> {
                    OrderOperateHistory history = new OrderOperateHistory();
                    history.setOrderId(omsOrderDeliveryParam.getOrderId());
                    history.setCreateTime(new Date());
                    history.setOperateMan("后台管理员");
                    history.setOrderStatus(2);
                    history.setNote("完成发货");
                    return history;
                }).map(h -> orderOperateHistoryDao.insert(h))
                .mapToInt(i -> i)
                .sum();

        return count;
    }

    @Override
    public int close(List<Long> ids, String note) {
        Order record = new Order();
        record.setStatus(4);
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Order::getId, ids);

        int count = orderDao.updateStatusByIds(4, ids);

        int sum = ids.stream().map(orderId -> {
            OrderOperateHistory history = new OrderOperateHistory();
            history.setOrderId(orderId);
            history.setCreateTime(new Date());
            history.setOperateMan("后台管理员");
            history.setOrderStatus(4);
            history.setNote("订单关闭:" + note);
            return history;
        }).map(h -> orderOperateHistoryDao.insert(h))
                .mapToInt(i -> i)
                .sum();
        log.debug("record history success = {}", sum);
        return count;
    }

    @Override
    public int delete(List<Long> ids) {
        int count = orderDao.deleteBatchIds(ids);
        log.debug("delete count = {}", count);
        return count;
    }

    @Override
    public OrderDetail detail(Long id) {
        return orderDao.getDetail(id);
    }

    @Override
    public int updateReceiverInfo(ReceiverInfoParam receiverInfoParam) {
        Long orderId = receiverInfoParam.getOrderId();
        Order order = orderDao.selectById(orderId);
        BeanUtils.copyProperties(receiverInfoParam, order);
        order.setModifyTime(new Date());
        int count = orderDao.updateById(order);

        //插入操作记录
        OrderOperateHistory history = new OrderOperateHistory();
        history.setOrderId(orderId);
        history.setCreateTime(new Date());
        history.setOperateMan("后台管理员");
        history.setOrderStatus(receiverInfoParam.getStatus());
        history.setNote("修改收货人信息");
        orderOperateHistoryDao.insert(history);
        return count;
    }

    @Override
    public int updateMoneyInfo(MoneyInfoParam moneyInfoParam) {

        Long orderId = moneyInfoParam.getOrderId();
        Order order = orderDao.selectById(orderId);
        BeanUtils.copyProperties(moneyInfoParam, order);
        order.setModifyTime(new Date());
        int count = orderDao.updateById(order);

        //插入操作记录
        OrderOperateHistory history = new OrderOperateHistory();
        history.setOrderId(moneyInfoParam.getOrderId());
        history.setCreateTime(new Date());
        history.setOperateMan("后台管理员");
        history.setOrderStatus(moneyInfoParam.getStatus());
        history.setNote("修改费用信息");
        orderOperateHistoryDao.insert(history);
        return count;
    }

    @Override
    public int updateNote(Long id, String note, Integer status) {
        Order order = new Order();
        order.setId(id);
        order.setNote(note);
        order.setModifyTime(new Date());
        int count = orderDao.updateNoteByOrderId(id, note, new Date());
        OrderOperateHistory history = new OrderOperateHistory();
        history.setOrderId(id);
        history.setCreateTime(new Date());
        history.setOperateMan("后台管理员");
        history.setOrderStatus(status);
        history.setNote("修改备注信息：" + note);
        orderOperateHistoryDao.insert(history);
        return count;
    }

}
