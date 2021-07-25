package com.lsz.mall.portal.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsz.mall.base.entity.Product;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.portal.dao.ProductDao;
import com.lsz.mall.portal.entity.ProductDetailVO;
import com.lsz.mall.portal.entity.ProductSearchGoodsVO;
import com.lsz.mall.portal.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Override
    public CommonPage<ProductSearchGoodsVO> search(String keyword, Long goodsCategoryId,
                                                   String orderBy, Integer pageNumber, Integer pageSize) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();

        boolean existKeyword = StrUtil.isNotBlank(keyword);
        wrapper.eq(goodsCategoryId != null, Product::getProductCategoryId, goodsCategoryId)
                .and(existKeyword, w -> {
                    w.eq(Product::getName, keyword)
                            .or()
                            .eq(Product::getDescription, keyword)
                            .or()
                            .eq(Product::getProductCategoryName, keyword)
                            .or()
                            .eq(Product::getBrandName, keyword)
                            .or()
                            .eq(Product::getSubTitle, keyword);
                });
        IPage<Product> page = productDao.selectPage(new Page<>(pageNumber, pageSize), wrapper);
        List<ProductSearchGoodsVO> result = page.getRecords()
                .stream()
                .map(p -> new ProductSearchGoodsVO(p))
                .collect(Collectors.toList());
        return CommonPage.restPage(page, result);
    }

    @Override
    public ProductDetailVO getDetail(Long goodsId) {
        Product product = productDao.selectById(goodsId);
        ProductDetailVO productDetailVO = new ProductDetailVO(product);
        return productDetailVO;
    }
}
