package com.lsz.mall.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsz.mall.base.entity.HomeAdvertise;
import com.lsz.mall.base.entity.HomeNewProduct;
import com.lsz.mall.base.entity.HomeRecommendProduct;
import com.lsz.mall.base.entity.Product;
import com.lsz.mall.portal.dao.HomeAdvertiseDao;
import com.lsz.mall.portal.dao.HomeNewProductDao;
import com.lsz.mall.portal.dao.HomeRecommendProductDao;
import com.lsz.mall.portal.dao.ProductDao;
import com.lsz.mall.portal.entity.HomeCarouselPicVO;
import com.lsz.mall.portal.entity.HomeConfigGoodsVO;
import com.lsz.mall.portal.entity.HomeInfoVO;
import com.lsz.mall.portal.service.HomeService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class HomeServiceImpl implements HomeService {

    @Autowired
    HomeAdvertiseDao homeAdvertiseDao;

    @Autowired
    ProductDao productDao;

    @Autowired
    HomeNewProductDao homeNewProductDao;

    @Autowired
    HomeRecommendProductDao homeRecommendProductDao;

    @Autowired
    RedissonClient redisson;

    @Override
    public HomeInfoVO homeInfos() {
        RBucket<HomeInfoVO> homeInfoBucket = redisson.getBucket("mall:home:infos");
        HomeInfoVO result = homeInfoBucket.get();
        if (result != null) {
            return result;
        }

        LambdaQueryWrapper<HomeAdvertise> advertiseWrapper = new LambdaQueryWrapper<>();
        advertiseWrapper.eq(HomeAdvertise::getStatus, 1)
                .orderByDesc(HomeAdvertise::getSort);
        List<HomeAdvertise> homeAdvertises = homeAdvertiseDao.selectList(advertiseWrapper);

        List<HomeCarouselPicVO> carouselVOS = homeAdvertises.stream()
                .map(h -> new HomeCarouselPicVO(h))
                .collect(Collectors.toList());

        // 热销，新品，推荐
        LambdaQueryWrapper<HomeNewProduct> homeNewProductWrapper = new LambdaQueryWrapper<HomeNewProduct>()
                .eq(HomeNewProduct::getRecommendStatus, 1).orderByDesc(HomeNewProduct::getSort);
        Set<Long> homeNewProductIds = Optional.ofNullable(homeNewProductDao.selectList(homeNewProductWrapper))
                .orElseGet(ArrayList::new)
                .stream()
                .map(HomeNewProduct::getProductId)
                .collect(Collectors.toSet());


        LambdaQueryWrapper<HomeRecommendProduct> homeRecommendProductWrapper = new LambdaQueryWrapper<HomeRecommendProduct>()
                .eq(HomeRecommendProduct::getRecommendStatus, 1).orderByDesc(HomeRecommendProduct::getSort);
        Set<Long> homeRecommendProdctIds = Optional.ofNullable(homeRecommendProductDao.selectList(homeRecommendProductWrapper))
                .orElseGet(ArrayList::new)
                .stream()
                .map(HomeRecommendProduct::getProductId)
                .collect(Collectors.toSet());

        Set<Long> productIds = new HashSet<>(homeNewProductIds.size() + homeRecommendProdctIds.size());
        productIds.addAll(homeNewProductIds);
        productIds.addAll(homeRecommendProdctIds);

        LambdaQueryWrapper<Product> productWrapper = new LambdaQueryWrapper<Product>()
                .in(!productIds.isEmpty(), Product::getId, productIds);
        List<Product> products = productDao.selectList(productWrapper);
        Map<Long, Product> productId2Product = products.stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        List<HomeConfigGoodsVO> homeNewProducts = homeNewProductIds
                .stream()
                .map(id -> productId2Product.get(id))
                .filter(Objects::nonNull)
                .map(p -> new HomeConfigGoodsVO(p))
                .collect(Collectors.toList());

        List<HomeConfigGoodsVO> homeRecommendProducts = homeRecommendProdctIds
                .stream()
                .map(id -> productId2Product.get(id))
                .filter(Objects::nonNull)
                .map(p -> new HomeConfigGoodsVO(p))
                .collect(Collectors.toList());


        result = new HomeInfoVO(carouselVOS, homeNewProducts, homeRecommendProducts, homeRecommendProducts);
        homeInfoBucket.set(result);
        return result;
    }
}
