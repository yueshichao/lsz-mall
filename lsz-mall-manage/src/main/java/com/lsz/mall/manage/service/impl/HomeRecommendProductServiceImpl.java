package com.lsz.mall.manage.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsz.mall.base.entity.HomeRecommendProduct;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.manage.dao.HomeRecommendProductDao;
import com.lsz.mall.manage.service.HomeRecommendProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class HomeRecommendProductServiceImpl implements HomeRecommendProductService {

    @Autowired
    HomeRecommendProductDao homeRecommendProductDao;

    @Override
    public int create(List<HomeRecommendProduct> homeRecommendProductList) {
        int count = homeRecommendProductList.stream()
                .map(homeRecommendProduct -> {
                    if (homeRecommendProduct.getRecommendStatus() == null) {
                        homeRecommendProduct.setRecommendStatus(1);
                    }
                    return homeRecommendProductDao.insert(homeRecommendProduct);
                })
                .mapToInt(i -> i)
                .sum();
        return count;
    }

    @Override
    public int updateSort(Long id, Integer sort) {
        LambdaQueryWrapper<HomeRecommendProduct> wrapper = new LambdaQueryWrapper<HomeRecommendProduct>()
                .eq(HomeRecommendProduct::getId, id);
        HomeRecommendProduct homeRecommendProduct = new HomeRecommendProduct();
        homeRecommendProduct.setSort(sort);
        return homeRecommendProductDao.update(homeRecommendProduct, wrapper);
    }

    @Override
    public int delete(List<Long> ids) {
        return homeRecommendProductDao.deleteBatchIds(ids);
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        LambdaQueryWrapper<HomeRecommendProduct> wrapper = new LambdaQueryWrapper<HomeRecommendProduct>()
                .in(HomeRecommendProduct::getId, ids);
        HomeRecommendProduct homeRecommendProduct = new HomeRecommendProduct();
        homeRecommendProduct.setRecommendStatus(recommendStatus);
        return homeRecommendProductDao.update(homeRecommendProduct, wrapper);
    }

    @Override
    public CommonPage<HomeRecommendProduct> getPage(String productName, Integer recommendStatus, Integer pageSize, Integer pageNum) {
        LambdaQueryWrapper<HomeRecommendProduct> wrapper = new LambdaQueryWrapper<HomeRecommendProduct>()
                .like(StrUtil.isNotBlank(productName), HomeRecommendProduct::getProductName, productName)
                .eq(recommendStatus != null, HomeRecommendProduct::getRecommendStatus, recommendStatus);
        IPage<HomeRecommendProduct> page = homeRecommendProductDao.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return CommonPage.restPage(page);
    }
}
