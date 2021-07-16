package com.lsz.mall.manage.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsz.mall.base.entity.AdminResource;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.manage.dao.AdminResourceDao;
import com.lsz.mall.manage.service.AdminResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class AdminResourceServiceImpl implements AdminResourceService {

    @Autowired
    AdminResourceDao resourceDao;

    @Override
    public List<AdminResource> listAll() {
        return resourceDao.selectList(null);
    }

    @Override
    public int create(AdminResource adminResource) {
        adminResource.setCreateTime(new Date());
        return 0;
    }

    @Override
    public int update(Long id, AdminResource adminResource) {
        adminResource.setId(id);
        return resourceDao.updateById(adminResource);
    }

    @Override
    public AdminResource getItem(Long id) {
        return resourceDao.selectById(id);
    }

    @Override
    public int delete(Long id) {
        return resourceDao.deleteById(id);
    }

    @Override
    public CommonPage<AdminResource> getPage(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum) {
        LambdaQueryWrapper<AdminResource> wrapper = new LambdaQueryWrapper<AdminResource>()
                .eq(categoryId != null, AdminResource::getCategoryId, categoryId)
                .like(StrUtil.isNotBlank(nameKeyword), AdminResource::getName, nameKeyword)
                .like(StrUtil.isNotBlank(urlKeyword), AdminResource::getUrl, urlKeyword);
        IPage<AdminResource> page = resourceDao.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return CommonPage.restPage(page);
    }


}
