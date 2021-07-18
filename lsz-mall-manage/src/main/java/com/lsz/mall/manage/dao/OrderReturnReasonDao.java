package com.lsz.mall.manage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsz.mall.base.entity.OrderReturnReason;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface OrderReturnReasonDao extends BaseMapper<OrderReturnReason> {


    int updateStatusByIds(@Param("status") Integer status, @Param("ids") List<Long> ids);

}
