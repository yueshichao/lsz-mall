package com.lsz.mall.manage.service;

import com.lsz.mall.base.entity.ResourceCategory;

import java.util.List;

public interface ResourceCategoryService {

    List<ResourceCategory> listAll();

    int create(ResourceCategory resourceCategory);

    int update(Long id, ResourceCategory resourceCategory);

    int delete(Long id);

}
