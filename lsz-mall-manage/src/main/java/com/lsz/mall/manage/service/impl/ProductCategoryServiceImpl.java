package com.lsz.mall.manage.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsz.mall.base.entity.*;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.manage.dao.ProductCategoryAttrRelationDao;
import com.lsz.mall.manage.dao.ProductCategoryDao;
import com.lsz.mall.manage.service.ProductCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    ProductCategoryDao productCategoryDao;

    @Autowired
    ProductCategoryAttrRelationDao productCategoryAttrRelationDao;

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


        return count;
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

    @Override
    public int update(Long id, ProductCategoryParam ProductCategoryParam) {
        return 0;
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
        return 0;
    }

    @Override
    public ProductCategory getItem(Long id) {
        return null;
    }

    @Override
    public int updateNavStatus(List<Long> ids, Integer navStatus) {
        return 0;
    }

    @Override
    public int updateShowStatus(List<Long> ids, Integer showStatus) {
        return 0;
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
