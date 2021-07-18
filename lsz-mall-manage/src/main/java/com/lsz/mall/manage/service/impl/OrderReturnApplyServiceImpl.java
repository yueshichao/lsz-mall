package com.lsz.mall.manage.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsz.mall.base.entity.OrderReturnApply;
import com.lsz.mall.base.entity.OrderReturnApplyResult;
import com.lsz.mall.base.entity.ReturnApplyQueryParam;
import com.lsz.mall.base.entity.UpdateStatusParam;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.manage.dao.OrderReturnApplyDao;
import com.lsz.mall.manage.service.OrderReturnApplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class OrderReturnApplyServiceImpl implements OrderReturnApplyService {


    @Autowired
    OrderReturnApplyDao orderReturnApplyDao;

    @Override
    public CommonPage<OrderReturnApply> getPage(ReturnApplyQueryParam queryParam, Integer pageSize, Integer pageNum) {

        LambdaQueryWrapper<OrderReturnApply> wrapper = new LambdaQueryWrapper<OrderReturnApply>()
                .eq(queryParam.getStatus() != null, OrderReturnApply::getStatus, queryParam.getStatus())
                .eq(queryParam.getId() != null, OrderReturnApply::getId, queryParam.getId())
                .eq(StrUtil.isNotBlank(queryParam.getCreateTime()), OrderReturnApply::getCreateTime, queryParam.getCreateTime())
                .eq(StrUtil.isNotBlank(queryParam.getHandleMan()), OrderReturnApply::getHandleMan, queryParam.getHandleMan())
                .eq(StrUtil.isNotBlank(queryParam.getHandleTime()), OrderReturnApply::getHandleTime, queryParam.getHandleTime())
                .and(StrUtil.isNotBlank(queryParam.getReceiverKeyword()), w -> {
                    w.like(OrderReturnApply::getReturnName, queryParam.getReceiverKeyword())
                            .like(OrderReturnApply::getReturnPhone, queryParam.getReceiverKeyword());
                });

        IPage<OrderReturnApply> page = orderReturnApplyDao.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return CommonPage.restPage(page);
    }

    @Override
    public int delete(List<Long> ids) {
        return orderReturnApplyDao.deleteBatchIds(ids);
    }

    @Override
    public OrderReturnApplyResult getItem(Long id) {
        return orderReturnApplyDao.getDetail(id);
    }

    @Override
    public int updateStatus(Long id, UpdateStatusParam statusParam) {
        Integer status = statusParam.getStatus();
        OrderReturnApply returnApply = orderReturnApplyDao.selectById(id);
        if (status.equals(1)) {
            //确认退货
            returnApply.setId(id);
            returnApply.setStatus(1);
            returnApply.setReturnAmount(statusParam.getReturnAmount());
            returnApply.setCompanyAddressId(statusParam.getCompanyAddressId());
            returnApply.setHandleTime(new Date());
            returnApply.setHandleMan(statusParam.getHandleMan());
            returnApply.setHandleNote(statusParam.getHandleNote());
        } else if (status.equals(2)) {
            //完成退货
            returnApply.setId(id);
            returnApply.setStatus(2);
            returnApply.setReceiveTime(new Date());
            returnApply.setReceiveMan(statusParam.getReceiveMan());
            returnApply.setReceiveNote(statusParam.getReceiveNote());
        } else if (status.equals(3)) {
            //拒绝退货
            returnApply.setId(id);
            returnApply.setStatus(3);
            returnApply.setHandleTime(new Date());
            returnApply.setHandleMan(statusParam.getHandleMan());
            returnApply.setHandleNote(statusParam.getHandleNote());
        } else {
            return 0;
        }
        return orderReturnApplyDao.updateById(returnApply);
    }
}
