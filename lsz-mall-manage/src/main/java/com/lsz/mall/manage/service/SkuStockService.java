package com.lsz.mall.manage.service;


import com.lsz.mall.base.entity.SkuStock;

import java.util.List;

public interface SkuStockService {

    List<SkuStock> getList(Long pid, String keyword);

    int update(Long pid, List<SkuStock> skuStockList);

}
