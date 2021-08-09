package com.lsz.mall.portal.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lsz.mall.base.entity.ProductCategory;
import com.lsz.mall.base.entity.ProductCategoryBase;
import com.lsz.mall.portal.dao.ProductCategoryDao;
import com.lsz.mall.portal.entity.FirstCategoryVO;
import com.lsz.mall.portal.entity.SecondLevelCategoryVO;
import com.lsz.mall.portal.entity.ThirdLevelCategoryVO;
import com.lsz.mall.portal.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    ProductCategoryDao productCategoryDao;

    @Autowired
    RedissonClient redisson;

    @Override
    public List<FirstCategoryVO> getCategories() {
        List<FirstCategoryVO> result;
        RBucket<List<FirstCategoryVO>> allCategoryBucket = redisson.getBucket("mall:category:all");
        result = allCategoryBucket.get();
        if (result != null) {
            return result;
        }

        log.debug("分类列表缓存失效，从数据库中读取~");
        LambdaQueryWrapper<ProductCategory> categoryWrapper = new LambdaQueryWrapper<>();
        categoryWrapper.eq(ProductCategoryBase::getShowStatus, 1);
        List<ProductCategory> categories = productCategoryDao.selectList(categoryWrapper);

        int rootLevel = 0;
//        int rootId = 0;
        Map<Integer, Map<Long, List<ProductCategory>>> level2ParentId2List = categories.stream().collect(Collectors.groupingBy(ProductCategory::getLevel, Collectors.groupingBy(ProductCategoryBase::getParentId)));
        List<ProductCategory> firstLevelCategories = level2ParentId2List.getOrDefault(rootLevel, new HashMap<>()).getOrDefault(0L, new ArrayList<>());
        result = firstLevelCategories.stream()
                .map(f -> new FirstCategoryVO(f))
                .collect(Collectors.toList());
        for (FirstCategoryVO firstCategoryVO : result) {
            Long categoryId = firstCategoryVO.getCategoryId();
            List<ProductCategory> secondCategories = level2ParentId2List.getOrDefault(rootLevel + 1, new HashMap<>()).getOrDefault(categoryId, new ArrayList<>());
            // 二级分类
            List<SecondLevelCategoryVO> secondLevelCategoryVOS = secondCategories.stream()
                    .map(s -> new SecondLevelCategoryVO(s, categoryId))
                    .collect(Collectors.toList());
            firstCategoryVO.setSecondLevelCategoryVOS(secondLevelCategoryVOS);
            // 三级分类
            for (SecondLevelCategoryVO secondLevelCategoryVO : secondLevelCategoryVOS) {
                Long secondCategoryId = secondLevelCategoryVO.getCategoryId();
                List<ProductCategory> thirdCategories = level2ParentId2List.getOrDefault(rootLevel + 2, new HashMap<>()).getOrDefault(secondCategoryId, new ArrayList<>());
                List<ThirdLevelCategoryVO> thirdLevelCategoryVOS = thirdCategories.stream()
                        .map(t -> new ThirdLevelCategoryVO(t))
                        .collect(Collectors.toList());
                secondLevelCategoryVO.setThirdLevelCategoryVOS(thirdLevelCategoryVOS);
            }

        }

        allCategoryBucket.set(result, 5, TimeUnit.HOURS);
        return result;
    }

}
