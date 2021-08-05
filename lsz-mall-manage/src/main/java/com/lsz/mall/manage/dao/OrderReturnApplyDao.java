package com.lsz.mall.manage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsz.mall.base.entity.OrderReturnApply;
import com.lsz.mall.base.entity.OrderReturnApplyResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface OrderReturnApplyDao extends BaseMapper<OrderReturnApply> {


    /**
     * 获取申请详情
     */
    OrderReturnApplyResult getDetail(@Param("id")Long id);

}
