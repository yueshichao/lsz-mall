package com.lsz.mall.manage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsz.mall.base.entity.Order;
import com.lsz.mall.base.entity.OrderDeliveryParam;
import com.lsz.mall.base.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Mapper
@Component
public interface OrderDao extends BaseMapper<Order> {

    /**
     * 批量发货
     */
    int delivery(@Param("list") List<OrderDeliveryParam> deliveryParamList);

    int updateStatusByIds(@Param("status") Integer status, @Param("ids") List<Long> ids);

    /**
     * 获取订单详情
     */
    OrderDetail getDetail(@Param("id") Long id);

    int updateNoteByOrderId(@Param("id") Long id, @Param("note") String note, @Param("modifyTime") Date modifyTime);
}
