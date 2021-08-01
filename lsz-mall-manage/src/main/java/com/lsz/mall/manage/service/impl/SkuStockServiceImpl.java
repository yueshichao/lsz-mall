package com.lsz.mall.manage.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lsz.mall.base.entity.SkuStock;
import com.lsz.mall.manage.dao.SkuStockDao;
import com.lsz.mall.manage.service.SkuStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class SkuStockServiceImpl implements SkuStockService {


    @Autowired
    SkuStockDao skuStockDao;

    @Override
    public List<SkuStock> getList(Long pid, String keyword) {

        LambdaQueryWrapper<SkuStock> wrapper = new LambdaQueryWrapper<SkuStock>()
                .eq(SkuStock::getProductId, pid)
                .like(StrUtil.isNotBlank(keyword), SkuStock::getSkuCode, keyword);

        return skuStockDao.selectList(wrapper);
    }

    @Override
    public int update(Long pid, List<SkuStock> skuStockList) {
        skuStockList.forEach(s -> s.setProductId(pid));
        return skuStockDao.replaceList(skuStockList);
    }

}
