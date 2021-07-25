package com.lsz.mall.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsz.mall.base.entity.HomeAdvertise;
import com.lsz.mall.base.entity.Product;
import com.lsz.mall.portal.dao.HomeAdvertiseDao;
import com.lsz.mall.portal.dao.ProductDao;
import com.lsz.mall.portal.entity.HomeInfoVO;
import com.lsz.mall.portal.entity.HomeCarouselPicVO;
import com.lsz.mall.portal.entity.HomeConfigGoodsVO;
import com.lsz.mall.portal.service.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HomeServiceImpl implements HomeService {

    @Autowired
    HomeAdvertiseDao homeAdvertiseDao;

    @Autowired
    ProductDao productDao;

    @Override
    public HomeInfoVO homeInfos() {

        LambdaQueryWrapper<HomeAdvertise> advertiseWrapper = new LambdaQueryWrapper<>();
        advertiseWrapper.eq(HomeAdvertise::getStatus, 1)
                .orderByDesc(HomeAdvertise::getSort);
        List<HomeAdvertise> homeAdvertises = homeAdvertiseDao.selectList(advertiseWrapper);

        List<HomeCarouselPicVO> carouselVOS = homeAdvertises.stream()
                .map(h -> new HomeCarouselPicVO(h))
                .collect(Collectors.toList());

//        LambdaQueryWrapper<Product> productWrapper = new LambdaQueryWrapper<>();
        IPage<Product> hotPage = productDao.selectPage(new Page<>(1, 5), null);
        List<HomeConfigGoodsVO> hotProducts = hotPage.getRecords().stream()
                .map(r -> new HomeConfigGoodsVO(r))
                .collect(Collectors.toList());


        return new HomeInfoVO(carouselVOS, hotProducts, hotProducts, hotProducts);
    }
}
