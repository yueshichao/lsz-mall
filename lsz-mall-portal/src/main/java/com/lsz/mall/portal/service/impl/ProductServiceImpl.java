package com.lsz.mall.portal.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsz.mall.base.entity.Product;
import com.lsz.mall.base.entity.SkuStock;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.portal.dao.ProductDao;
import com.lsz.mall.portal.dao.SkuStockDao;
import com.lsz.mall.portal.entity.ProductDetailVO;
import com.lsz.mall.portal.entity.ProductSearchGoodsVO;
import com.lsz.mall.portal.entity.SkuStockVO;
import com.lsz.mall.portal.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Autowired
    SkuStockDao skuStockDao;

    @Override
    public CommonPage<ProductSearchGoodsVO> search(String keyword, Long goodsCategoryId,
                                                   String orderBy, Integer pageNumber, Integer pageSize) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();

        boolean existKeyword = StrUtil.isNotBlank(keyword);
        wrapper.eq(goodsCategoryId != null, Product::getProductCategoryId, goodsCategoryId)
                .eq(Product::getDeleteStatus, 0)
                .eq(Product::getPublishStatus, 1)
                .and(existKeyword, w -> {
                    w.like(Product::getName, keyword)
                            .or()
                            .like(Product::getDescription, keyword)
                            .or()
                            .like(Product::getProductCategoryName, keyword)
                            .or()
                            .like(Product::getBrandName, keyword)
                            .or()
                            .like(Product::getSubTitle, keyword);
                });
        IPage<Product> page = productDao.selectPage(new Page<>(pageNumber, pageSize), wrapper);
        List<ProductSearchGoodsVO> result = page.getRecords()
                .stream()
                .map(p -> new ProductSearchGoodsVO(p))
                .collect(Collectors.toList());
        return CommonPage.restPage(page, result);
    }

    @Override
    public ProductDetailVO getDetail(Long productId) {

        Product product = productDao.selectById(productId);
        LambdaQueryWrapper<SkuStock> skuStockWrapper = new LambdaQueryWrapper<SkuStock>()
                .eq(SkuStock::getProductId, productId);
        List<SkuStock> skuStocks = skuStockDao.selectList(skuStockWrapper);
        List<SkuStockVO> skuStockVOS = Optional.ofNullable(skuStocks)
                .orElseGet(ArrayList::new)
                .stream()
                .map(s -> new SkuStockVO(s))
                .collect(Collectors.toList());

        ProductDetailVO productDetailVO = new ProductDetailVO(product);
        productDetailVO.setSkuStockList(skuStockVOS);
        return productDetailVO;
    }
}
