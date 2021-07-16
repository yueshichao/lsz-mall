package com.lsz.mall.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lsz.mall.base.entity.ResourceCategory;
import com.lsz.mall.manage.dao.ResourceCategoryDao;
import com.lsz.mall.manage.service.ResourceCategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class ResourceCategoryServiceImpl implements ResourceCategoryService {

    @Autowired
    ResourceCategoryDao resourceCategoryDao;

    @Override
    public List<ResourceCategory> listAll() {
        LambdaQueryWrapper<ResourceCategory> wrapper = new LambdaQueryWrapper<ResourceCategory>()
                .orderByDesc(ResourceCategory::getSort);
        return resourceCategoryDao.selectList(wrapper);
    }

    @Override
    public int create(ResourceCategory resourceCategory) {
        resourceCategory.setCreateTime(new Date());
        return resourceCategoryDao.insert(resourceCategory);
    }

    @Override
    public int update(Long id, ResourceCategory resourceCategory) {
        resourceCategory.setId(id);
        return resourceCategoryDao.updateById(resourceCategory);
    }

    @Override
    public int delete(Long id) {
        return resourceCategoryDao.deleteById(id);
    }
}
