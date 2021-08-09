package com.lsz.mall.manage.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsz.mall.base.entity.*;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.manage.dao.ProductCategoryAttrRelationDao;
import com.lsz.mall.manage.dao.ProductCategoryDao;
import com.lsz.mall.manage.dao.ProductDao;
import com.lsz.mall.manage.service.ProductCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    ProductCategoryDao productCategoryDao;

    @Autowired
    ProductCategoryAttrRelationDao productCategoryAttrRelationDao;

    @Autowired
    RedissonClient redisson;

    @Resource(name = "delayDeletedExecutor")
    ThreadPoolExecutor delayDeletedExecutor;

    @Override
    public int create(ProductCategoryParam productCategoryParam) {
        ProductCategory productCategory = new ProductCategory();
        BeanUtils.copyProperties(productCategoryParam, productCategory);
        // 设置分类level层级
        setCategoryLevel(productCategory);

        int count = productCategoryDao.insert(productCategory);

        // 分类、属性关联
        List<Long> productAttributeIdList = productCategoryParam.getProductAttributeIdList();
        insertRelationList(productCategory.getId(), productAttributeIdList);

        deleteCategoryCache();

        return count;
    }

    private void deleteCategoryCache() {
        // 删除缓存
        String key = "mall:category:all";
        RBucket<Object> bucket = redisson.getBucket(key);
        bucket.delete();
        // 此种方式延迟删除，如果任务积压，会在超过1s的时间删除
        delayDeletedExecutor.execute(() -> {
            ThreadUtil.sleep(1000);
            log.debug("删除key = {}", key);
            bucket.delete();
        });
    }

    /**
     * 批量插入商品分类与筛选属性关系表
     *
     * @param productCategoryId      商品分类id
     * @param productAttributeIdList 相关商品筛选属性id集合
     */
    private void insertRelationList(Long productCategoryId, List<Long> productAttributeIdList) {
        if (CollectionUtil.isEmpty(productAttributeIdList)) {
            return;
        }

        List<ProductCategoryAttributeRelation> relations = productAttributeIdList.stream()
                .map(p -> new ProductCategoryAttributeRelation(productCategoryId, p)).collect(Collectors.toList());

        // 换成mysql的批量插入
        relations.stream().forEach(r -> productCategoryAttrRelationDao.insert(r));

    }

    @Autowired
    ProductDao productDao;

    @Override
    public int update(Long id, ProductCategoryParam productCategoryParam) {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(id);
        BeanUtils.copyProperties(productCategoryParam, productCategory);
        setCategoryLevel(productCategory);

        Product product = new Product();
        product.setProductCategoryName(productCategory.getName());
        LambdaQueryWrapper<Product> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Product::getProductCategoryId, productCategory.getId());
        productDao.update(product, wrapper);


        // 重新建立连接
        LambdaQueryWrapper<ProductCategoryAttributeRelation> relationWrapper = new LambdaQueryWrapper<>();
        relationWrapper.eq(ProductCategoryAttributeRelation::getProductCategoryId, id);
        productCategoryAttrRelationDao.delete(relationWrapper);
        List<Long> productAttributeIdList = productCategoryParam.getProductAttributeIdList();
        if (CollectionUtil.isNotEmpty(productAttributeIdList)) {
            List<ProductCategoryAttributeRelation> relations = productAttributeIdList.stream().map(attrId -> new ProductCategoryAttributeRelation(id, attrId)).collect(Collectors.toList());
            relations.forEach(r -> productCategoryAttrRelationDao.insert(r));
        }

        int count = productCategoryDao.updateById(productCategory);
        deleteCategoryCache();

        return count;
    }

    @Override
    public CommonPage<ProductCategory> getPage(Long parentId, Integer pageSize, Integer pageNum) {
        QueryWrapper<ProductCategory> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(ProductCategory::getParentId, parentId)
                .orderByDesc(ProductCategory::getSort);
        Page<ProductCategory> p = new Page<>(pageNum, pageSize);
        IPage<ProductCategory> page = productCategoryDao.selectPage(p, queryWrapper);
        return CommonPage.restPage(page);
    }

    @Override
    public int delete(Long id) {
        int count = productCategoryDao.deleteById(id);
        deleteCategoryCache();
        return count;
    }

    @Override
    public ProductCategory getItem(Long id) {
        return productCategoryDao.selectById(id);
    }

    @Override
    public int updateNavStatus(List<Long> ids, Integer navStatus) {
        int count = ids.stream()
                .map(id -> {
                    ProductCategory productCategory = new ProductCategory(id);
                    productCategory.setNavStatus(navStatus);
                    return productCategory;
                })
                .map(p -> productCategoryDao.updateById(p))
                .mapToInt(i -> i)
                .sum();
        deleteCategoryCache();
        return count;
    }

    @Override
    public int updateShowStatus(List<Long> ids, Integer showStatus) {
        int count = ids.stream()
                .map(id -> {
                    ProductCategory productCategory = new ProductCategory(id);
                    productCategory.setShowStatus(showStatus);
                    return productCategory;
                })
                .map(p -> productCategoryDao.updateById(p))
                .mapToInt(i -> i)
                .sum();
        deleteCategoryCache();
        return count;
    }

    @Override
    public List<ProductCategoryWithChildrenItem> listWithChildren() {

        // 查出所有数据
        List<ProductCategory> list = productCategoryDao.selectList(null);

        // 构造树
        Map<Long, List<ProductCategory>> listByParentId = list.stream().collect(Collectors.groupingBy(ProductCategoryBase::getParentId));

        ProductCategoryWithChildrenItem root = new ProductCategoryWithChildrenItem();
        root.setId(0L);
        Queue<ProductCategoryWithChildrenItem> q = new ArrayDeque<>();
        q.offer(root);
        while (!q.isEmpty()) {
            ProductCategoryWithChildrenItem item = q.poll();
            if (item == null) continue;
            Long parentId = item.getId();
            List<ProductCategory> productCategories = listByParentId.get(parentId);
            if (CollectionUtil.isEmpty(productCategories)) continue;
            List<ProductCategoryWithChildrenItem> children = productCategories.stream().map(p -> new ProductCategoryWithChildrenItem(p)).collect(Collectors.toList());
            item.setChildren(children);
            q.addAll(children);
        }

        return root.getChildren();
    }

    @Override
    public List<ProductCategory> getList(Integer currentLevel) {
        LambdaQueryWrapper<ProductCategory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ProductCategory::getLevel, currentLevel - 1);
        return productCategoryDao.selectList(wrapper);
    }


    /**
     * 根据分类的parentId设置分类的level
     */
    private void setCategoryLevel(ProductCategory productCategory) {
        //没有父分类时为一级分类
        if (productCategory.getParentId() == 0) {
            productCategory.setLevel(0);
        } else {
            //有父分类时选择根据父分类level设置
            QueryWrapper<ProductCategory> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda().eq(ProductCategory::getId, productCategory.getParentId());
            ProductCategory parentCategory = productCategoryDao.selectOne(queryWrapper);
//            ProductCategory parentCategory = productCategoryDao.selectByPrimaryKey(productCategory.getParentId());
            if (parentCategory != null) {
                productCategory.setLevel(parentCategory.getLevel() + 1);
            } else {
                productCategory.setLevel(0);
            }
        }
    }
}
