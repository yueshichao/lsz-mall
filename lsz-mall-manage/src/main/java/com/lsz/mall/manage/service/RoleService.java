package com.lsz.mall.manage.service;

import com.lsz.mall.base.entity.AdminMenu;
import com.lsz.mall.base.entity.AdminResource;
import com.lsz.mall.base.entity.AdminRole;
import com.lsz.mall.base.vo.CommonPage;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RoleService {


    List<AdminMenu> getMenuList(Long id);

    int create(AdminRole role);

    int update(Long id, AdminRole role);

    int delete(List<Long> ids);

    List<AdminRole> list();

    CommonPage<AdminRole> getPage(String keyword, Integer pageSize, Integer pageNum);

    List<AdminMenu> listMenu(Long roleId);

    List<AdminResource> listResource(Long roleId);

    @Transactional
    int allocMenu(Long roleId, List<Long> menuIds);

    @Transactional
    int allocResource(Long roleId, List<Long> resourceIds);

}
