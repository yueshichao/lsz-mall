package com.lsz.mall.manage.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsz.mall.base.entity.HomeNewProduct;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.manage.dao.HomeNewProductDao;
import com.lsz.mall.manage.service.HomeNewProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class HomeNewProductServiceImpl implements HomeNewProductService {

    @Autowired
    HomeNewProductDao homeNewProductDao;

    @Override
    public int create(List<HomeNewProduct> homeNewProductList) {
        int count = homeNewProductList.stream()
                .map(homeNewProduct -> {
                    if (homeNewProduct.getRecommendStatus() == null) {
                        homeNewProduct.setRecommendStatus(1);
                    }
                    return homeNewProductDao.insert(homeNewProduct);
                })
                .mapToInt(i -> i)
                .sum();
        return count;
    }

    @Override
    public int updateSort(Long id, Integer sort) {
        LambdaQueryWrapper<HomeNewProduct> wrapper = new LambdaQueryWrapper<HomeNewProduct>()
                .eq(HomeNewProduct::getId, id);
        HomeNewProduct homeNewProduct = new HomeNewProduct();
        homeNewProduct.setSort(sort);
        return homeNewProductDao.update(homeNewProduct, wrapper);
    }

    @Override
    public int delete(List<Long> ids) {
        return homeNewProductDao.deleteBatchIds(ids);
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        LambdaQueryWrapper<HomeNewProduct> wrapper = new LambdaQueryWrapper<HomeNewProduct>()
                .in(HomeNewProduct::getId, ids);
        HomeNewProduct homeNewProduct = new HomeNewProduct();
        homeNewProduct.setRecommendStatus(recommendStatus);
        return homeNewProductDao.update(homeNewProduct, wrapper);
    }

    @Override
    public CommonPage<HomeNewProduct> getPage(String productName, Integer recommendStatus, Integer pageSize, Integer pageNum) {
        LambdaQueryWrapper<HomeNewProduct> wrapper = new LambdaQueryWrapper<HomeNewProduct>()
                .like(StrUtil.isNotBlank(productName), HomeNewProduct::getProductName, productName)
                .eq(recommendStatus != null, HomeNewProduct::getRecommendStatus, recommendStatus);

        IPage<HomeNewProduct> page = homeNewProductDao.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return CommonPage.restPage(page);
    }

}
