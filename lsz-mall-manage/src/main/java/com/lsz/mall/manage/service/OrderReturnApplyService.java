package com.lsz.mall.manage.service;

import com.lsz.mall.base.entity.OrderReturnApply;
import com.lsz.mall.base.entity.OrderReturnApplyResult;
import com.lsz.mall.base.entity.ReturnApplyQueryParam;
import com.lsz.mall.base.entity.UpdateStatusParam;
import com.lsz.mall.base.vo.CommonPage;

import java.util.List;

public interface OrderReturnApplyService {


    CommonPage<OrderReturnApply> getPage(ReturnApplyQueryParam queryParam, Integer pageSize, Integer pageNum);

    int delete(List<Long> ids);

    OrderReturnApplyResult getItem(Long id);

    int updateStatus(Long id, UpdateStatusParam statusParam);

}
