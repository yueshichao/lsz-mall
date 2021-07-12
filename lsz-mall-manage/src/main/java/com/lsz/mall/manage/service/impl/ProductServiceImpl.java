package com.lsz.mall.manage.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsz.mall.base.entity.Product;
import com.lsz.mall.base.entity.ProductParam;
import com.lsz.mall.base.entity.ProductQueryParam;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.manage.dao.*;
import com.lsz.mall.manage.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductDao productDao;

    @Autowired
    MemberPriceDao memberPriceDao;

    @Autowired
    ProductLadderDao productLadderDao;

    @Autowired
    ProductFullReductionDao productFullReductionDao;

    @Autowired
    ProductAttrValueDao productAttrValueDao;

    @Autowired
    SkuStockDao skuStockDao;

    @Autowired
    SubjectProductRelationDao subjectProductRelationDao;

    @Override
    public int create(ProductParam productParam) {
        Product product = new Product();
        BeanUtils.copyProperties(productParam, product);

        // 保存商品信息
        product.setId(null);
        int count = productDao.insert(product);

        // 保存商品的关联信息
        // 1. 会员价格
        Optional.ofNullable(productParam.getMemberPriceList())
                .ifPresent(list -> {
                    list.forEach(e -> memberPriceDao.insert(e));
                });

        // 2. 阶梯价格
        Optional.ofNullable(productParam.getProductLadderList())
                .ifPresent(list -> {
                    list.forEach(e -> productLadderDao.insert(e));
                });

        // 3. 满减价格
        Optional.ofNullable(productParam.getProductFullReductionList())
                .ifPresent(list -> {
                    list.forEach(e -> productFullReductionDao.insert(e));
                });

        // 4. 库存信息
        Optional.ofNullable(productParam.getSkuStockList())
                .ifPresent(list -> {
                    list.forEach(e -> skuStockDao.insert(e));
                });

        // 5. 商品规格
        Optional.ofNullable(productParam.getProductAttributeValueList())
                .ifPresent(list -> {
                    list.forEach(e -> productAttrValueDao.insert(e));
                });

        // 6. 专题
        Optional.ofNullable(productParam.getSubjectProductRelationList())
                .ifPresent(list -> {
                    list.forEach(e -> subjectProductRelationDao.insert(e));
                });


        return count;
    }

    @Override
    public CommonPage<Product> getPage(ProductQueryParam productQueryParam, Integer pageSize, Integer pageNum) {

        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(productQueryParam.getPublishStatus() != null, Product::getPublishStatus, productQueryParam.getPublishStatus())
                .eq(productQueryParam.getVerifyStatus() != null, Product::getVerifyStatus, productQueryParam.getVerifyStatus())
                .eq(productQueryParam.getBrandId() != null, Product::getBrandId, productQueryParam.getBrandId())
                .eq(productQueryParam.getProductCategoryId() != null, Product::getProductCategoryId, productQueryParam.getProductCategoryId())
                .eq(StrUtil.isNotBlank(productQueryParam.getProductSn()), Product::getProductSn, productQueryParam.getProductSn())
                .like(StrUtil.isNotBlank(productQueryParam.getKeyword()), Product::getName, productQueryParam.getKeyword());

        IPage<Product> page = productDao.selectPage(new Page<>(pageNum, pageSize), queryWrapper);

        return CommonPage.restPage(page);
    }


}
