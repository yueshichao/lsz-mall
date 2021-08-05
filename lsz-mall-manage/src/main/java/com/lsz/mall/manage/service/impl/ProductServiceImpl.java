package com.lsz.mall.manage.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsz.mall.base.entity.*;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.manage.dao.*;
import com.lsz.mall.manage.service.HomeCacheService;
import com.lsz.mall.manage.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.webresources.WarResource;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
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

    @Autowired
    HomeCacheService homeCacheService;

    @Override
    public int create(ProductParam productParam) {
        Product product = new Product();
        BeanUtils.copyProperties(productParam, product);

        // 保存商品信息
        product.setId(null);
        int count = productDao.insert(product);
        Long productId = product.getId();

        // 保存商品的关联信息
        // 1. 会员价格
        Optional.ofNullable(productParam.getMemberPriceList())
                .ifPresent(list -> {
                    list.forEach(e -> e.setProductId(productId));
                    list.forEach(e -> memberPriceDao.insert(e));
                });

        // 2. 阶梯价格
        Optional.ofNullable(productParam.getProductLadderList())
                .ifPresent(list -> {
                    list.forEach(e -> e.setProductId(productId));
                    list.forEach(e -> productLadderDao.insert(e));
                });

        // 3. 满减价格
        Optional.ofNullable(productParam.getProductFullReductionList())
                .ifPresent(list -> {
                    list.forEach(e -> e.setProductId(productId));
                    list.forEach(e -> productFullReductionDao.insert(e));
                });

        // 4. 库存信息
        Optional.ofNullable(productParam.getSkuStockList())
                .ifPresent(list -> {
                    list.forEach(e -> e.setProductId(productId));
                    list.forEach(e -> skuStockDao.insert(e));
                });

        // 5. 商品规格
        Optional.ofNullable(productParam.getProductAttributeValueList())
                .ifPresent(list -> {
                    list.forEach(e -> e.setProductId(productId));
                    list.forEach(e -> productAttrValueDao.insert(e));
                });

        // 6. 专题
        Optional.ofNullable(productParam.getSubjectProductRelationList())
                .ifPresent(list -> {
                    list.forEach(e -> e.setProductId(productId));
                    list.forEach(e -> subjectProductRelationDao.insert(e));
                });

        homeCacheService.deleteHomeInfoCache();
        return count;
    }

    @Override
    public CommonPage<Product> getPage(ProductQueryParam productQueryParam, Integer pageSize, Integer pageNum) {

        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Product::getDeleteStatus, 0)
                .eq(productQueryParam.getPublishStatus() != null, Product::getPublishStatus, productQueryParam.getPublishStatus())
                .eq(productQueryParam.getVerifyStatus() != null, Product::getVerifyStatus, productQueryParam.getVerifyStatus())
                .eq(productQueryParam.getBrandId() != null, Product::getBrandId, productQueryParam.getBrandId())
                .eq(productQueryParam.getProductCategoryId() != null, Product::getProductCategoryId, productQueryParam.getProductCategoryId())
                .eq(StrUtil.isNotBlank(productQueryParam.getProductSn()), Product::getProductSn, productQueryParam.getProductSn())
                .like(StrUtil.isNotBlank(productQueryParam.getKeyword()), Product::getName, productQueryParam.getKeyword());

        IPage<Product> page = productDao.selectPage(new Page<>(pageNum, pageSize), queryWrapper);

        return CommonPage.restPage(page);
    }

    @Override
    public ProductResult getUpdateInfo(Long id) {
        ProductResult updateInfo = productDao.getUpdateInfo(id);
        log.debug("updateInfo = {}", JSON.toJSONString(updateInfo));

        return updateInfo;
    }

    @Override
    public int update(Long id, ProductParam productParam) {
        Product product = new Product();
        BeanUtils.copyProperties(productParam, product);

        // 保存商品信息
        Long productId = id;
        product.setId(id);
        int count = productDao.updateById(product);

        // 更新商品的关联信息
        // 1. 会员价格
        LambdaQueryWrapper<MemberPrice> memberPriceWrapper = new LambdaQueryWrapper<MemberPrice>()
                .eq(MemberPrice::getProductId, id);
        memberPriceDao.delete(memberPriceWrapper);
        Optional.ofNullable(productParam.getMemberPriceList())
                .ifPresent(list -> {
                    list.forEach(e -> e.setProductId(productId));
                    list.forEach(e -> memberPriceDao.insert(e));
                });

        // 2. 阶梯价格
        LambdaQueryWrapper<ProductLadder> productLadderWrapper = new LambdaQueryWrapper<ProductLadder>()
                .eq(ProductLadder::getProductId, id);
        productLadderDao.delete(productLadderWrapper);
        Optional.ofNullable(productParam.getProductLadderList())
                .ifPresent(list -> {
                    list.forEach(e -> e.setProductId(productId));
                    list.forEach(e -> productLadderDao.insert(e));
                });

        // 3. 满减价格
        LambdaQueryWrapper<ProductFullReduction> productFullReductionWrapper = new LambdaQueryWrapper<ProductFullReduction>()
                .eq(ProductFullReduction::getProductId, id);
        productFullReductionDao.delete(productFullReductionWrapper);
        Optional.ofNullable(productParam.getProductFullReductionList())
                .ifPresent(list -> {
                    list.forEach(e -> e.setProductId(productId));
                    list.forEach(e -> productFullReductionDao.insert(e));
                });

        // 4. 库存信息
        LambdaQueryWrapper<SkuStock> skuStockWrapper = new LambdaQueryWrapper<SkuStock>()
                .eq(SkuStock::getProductId, id);
        skuStockDao.delete(skuStockWrapper);
        Optional.ofNullable(productParam.getSkuStockList())
                .ifPresent(list -> {
                    list.forEach(e -> e.setProductId(productId));
                    list.forEach(e -> skuStockDao.insert(e));
                });

        // 5. 商品规格
        LambdaQueryWrapper<ProductAttrValue> productAttrValueWrapper = new LambdaQueryWrapper<ProductAttrValue>()
                .eq(ProductAttrValue::getProductId, id);
        productAttrValueDao.delete(productAttrValueWrapper);
        Optional.ofNullable(productParam.getProductAttributeValueList())
                .ifPresent(list -> {
                    list.forEach(e -> e.setProductId(productId));
                    list.forEach(e -> productAttrValueDao.insert(e));
                });

        // 6. 专题
        LambdaQueryWrapper<SubjectProductRelation> subjectProductRelationWrapper = new LambdaQueryWrapper<SubjectProductRelation>()
                .eq(SubjectProductRelation::getProductId, id);
        subjectProductRelationDao.delete(subjectProductRelationWrapper);
        Optional.ofNullable(productParam.getSubjectProductRelationList())
                .ifPresent(list -> {
                    list.forEach(e -> e.setProductId(productId));
                    list.forEach(e -> subjectProductRelationDao.insert(e));
                });


        homeCacheService.deleteHomeInfoCache();
        return count;
    }

    @Override
    public List<Product> list(String keyword) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(Product::getDeleteStatus, 0)
                .like(StrUtil.isNotBlank(keyword), Product::getName, keyword);
        return productDao.selectList(queryWrapper);
    }

    @Override
    public int updateVerifyStatus(List<Long> ids, Integer verifyStatus, String detail) {

        int count = updateByIds(ids, (product) -> {
            product.setVerifyStatus(verifyStatus);
            product.setDetailDesc(detail);
        });

        return count;
    }

    private int updateByIds(List<Long> ids, Consumer<Product> updateFieldFunc) {
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<Product>()
                .in(Product::getId, ids);
        Product product = new Product();
        updateFieldFunc.accept(product);
        return productDao.update(product, wrapper);
    }

    @Autowired
    HomeRecommendProductDao homeRecommendProductDao;

    @Autowired
    HomeNewProductDao homeNewProductDao;

    @Override
    public int updatePublishStatus(List<Long> ids, Integer publishStatus) {
        int count = updateByIds(ids, (product) -> {
            product.setPublishStatus(publishStatus);
        });
        homeCacheService.deleteHomeInfoCache();
        return count;
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        int count = updateByIds(ids, (product) -> {
            product.setRecommandStatus(recommendStatus);
        });
        homeCacheService.deleteHomeInfoCache();
        return count;
    }

    @Override
    public int updateNewStatus(List<Long> ids, Integer newStatus) {
        int count = updateByIds(ids, (product) -> {
            product.setNewStatus(newStatus);
        });

        homeCacheService.deleteHomeInfoCache();
        return count;
    }

    @Override
    public int updateDeleteStatus(List<Long> ids, Integer deleteStatus) {
        int count = updateByIds(ids, (product) -> {
            product.setDeleteStatus(deleteStatus);
        });

        homeCacheService.deleteHomeInfoCache();
        return count;
    }
}
