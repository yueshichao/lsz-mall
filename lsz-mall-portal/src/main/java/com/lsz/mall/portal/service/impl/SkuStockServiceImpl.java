package com.lsz.mall.portal.service.impl;

import com.lsz.mall.base.entity.SkuStock;
import com.lsz.mall.portal.dao.SkuStockDao;
import com.lsz.mall.portal.service.SkuStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class SkuStockServiceImpl implements SkuStockService {

    @Autowired
    SkuStockDao skuStockDao;

    @Override
    public int getValidSkuStock(Long productSkuId) {
        SkuStock skuStock = skuStockDao.selectById(productSkuId);
        int validStock = skuStock.getStock() - skuStock.getLockStock();
        log.debug("productSkuId = {}, validStock = {}", productSkuId, validStock);
        return validStock;
    }

}
