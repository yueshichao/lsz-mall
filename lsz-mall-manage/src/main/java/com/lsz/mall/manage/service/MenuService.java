package com.lsz.mall.manage.service;

import com.lsz.mall.base.entity.AdminMenu;
import com.lsz.mall.base.entity.AdminMenuNode;
import com.lsz.mall.base.vo.CommonPage;

import java.util.List;

public interface MenuService {

    int create(AdminMenu adminMenu);

    int update(Long id, AdminMenu adminMenu);

    AdminMenu getItem(Long id);

    int delete(Long id);

    CommonPage<AdminMenu> getPage(Long parentId, Integer pageSize, Integer pageNum);

    List<AdminMenuNode> treeList();

    int updateHidden(Long id, Integer hidden);

}
