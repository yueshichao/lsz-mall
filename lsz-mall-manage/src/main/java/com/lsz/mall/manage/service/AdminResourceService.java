package com.lsz.mall.manage.service;

import com.lsz.mall.base.entity.AdminResource;
import com.lsz.mall.base.vo.CommonPage;

import java.util.List;

public interface AdminResourceService {

    List<AdminResource> listAll();

    int create(AdminResource adminResource);

    int update(Long id, AdminResource adminResource);

    AdminResource getItem(Long id);

    int delete(Long id);

    CommonPage<AdminResource> getPage(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum);

}
