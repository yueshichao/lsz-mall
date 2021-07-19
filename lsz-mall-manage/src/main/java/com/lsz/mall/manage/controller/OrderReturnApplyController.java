package com.lsz.mall.manage.controller;

import com.lsz.mall.base.entity.*;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.manage.service.OrderReturnApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Api(description = "订单退货申请管理")
@RequestMapping("/returnApply")
public class OrderReturnApplyController {
    @Autowired
    private OrderReturnApplyService returnApplyService;

    @ApiOperation("分页查询退货申请")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage<CommonPage<OrderReturnApply>> list(ReturnApplyQueryParam queryParam,
                                                              @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                              @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        CommonPage<OrderReturnApply> returnApplyList = returnApplyService.getPage(queryParam, pageSize, pageNum);
        return ResponseMessage.ok(returnApplyList);
    }

    @ApiOperation("批量删除退货申请")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage delete(@RequestParam("ids") List<Long> ids) {
        int count = returnApplyService.delete(ids);
        if (count > 0) {
            return ResponseMessage.ok(count);
        }
        return ResponseMessage.error();
    }

    @ApiOperation("获取退货申请详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMessage getItem(@PathVariable Long id) {
        OrderReturnApplyResult result = returnApplyService.getItem(id);
        return ResponseMessage.ok(result);
    }

    @ApiOperation("修改退货申请状态")
    @RequestMapping(value = "/update/status/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMessage updateStatus(@PathVariable Long id, @RequestBody UpdateStatusParam statusParam) {
        int count = returnApplyService.updateStatus(id, statusParam);
        if (count > 0) {
            return ResponseMessage.ok(count);
        }
        return ResponseMessage.error();
    }

}
