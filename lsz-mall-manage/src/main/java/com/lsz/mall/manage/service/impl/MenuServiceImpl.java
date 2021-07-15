package com.lsz.mall.manage.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsz.mall.base.entity.AdminMenu;
import com.lsz.mall.base.entity.AdminMenuNode;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.manage.dao.AdminMenuDao;
import com.lsz.mall.manage.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MenuServiceImpl implements MenuService {

    @Autowired
    AdminMenuDao menuDao;

    @Override
    public int create(AdminMenu adminMenu) {
        adminMenu.setCreateTime(new Date());
        updateLevel(adminMenu);
        return menuDao.insert(adminMenu);
    }

    private void updateLevel(AdminMenu adminMenu) {
        if (adminMenu.getParentId() == 0) {
            //没有父菜单时为一级菜单
            adminMenu.setLevel(0);
        } else {
            //有父菜单时选择根据父菜单level设置
            LambdaQueryWrapper<AdminMenu> wrapper = new LambdaQueryWrapper<AdminMenu>()
                    .eq(AdminMenu::getParentId, adminMenu.getParentId());
            AdminMenu parentMenu = menuDao.selectOne(wrapper);
            if (parentMenu != null) {
                adminMenu.setLevel(parentMenu.getLevel() + 1);
            } else {
                adminMenu.setLevel(0);
            }
        }

    }

    @Override
    public int update(Long id, AdminMenu adminMenu) {
        adminMenu.setId(id);
        updateLevel(adminMenu);
        return menuDao.updateById(adminMenu);
    }

    @Override
    public AdminMenu getItem(Long id) {
        return menuDao.selectById(id);
    }

    @Override
    public int delete(Long id) {
        return menuDao.deleteById(id);
    }

    @Override
    public CommonPage<AdminMenu> getPage(Long parentId, Integer pageSize, Integer pageNum) {
        LambdaQueryWrapper<AdminMenu> wrapper = new LambdaQueryWrapper<AdminMenu>()
                .eq(AdminMenu::getParentId, parentId);
        IPage<AdminMenu> page = menuDao.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return CommonPage.restPage(page);
    }

    @Override
    public List<AdminMenuNode> treeList() {
        // 查到所有数据
        List<AdminMenu> list = menuDao.selectList(null);

        // 树化
        Map<Long, List<AdminMenu>> listByParentId = list.stream().collect(Collectors.groupingBy(AdminMenu::getParentId));

        AdminMenuNode root = new AdminMenuNode();
        root.setId(0L);
        Queue<AdminMenuNode> q = new ArrayDeque<>();
        q.offer(root);
        while (!q.isEmpty()) {
            AdminMenuNode item = q.poll();
            if (item == null) continue;
            Long parentId = item.getId();
            List<AdminMenu> adminMenus = listByParentId.get(parentId);
            if (CollectionUtil.isEmpty(adminMenus)) continue;
            List<AdminMenuNode> children = adminMenus.stream().map(p -> new AdminMenuNode(p)).collect(Collectors.toList());
            item.setChildren(children);
            q.addAll(children);
        }

        return root.getChildren();
    }

    @Override
    public int updateHidden(Long id, Integer hidden) {
        AdminMenu adminMenu = menuDao.selectById(id);
        adminMenu.setHidden(hidden);
        return menuDao.updateById(adminMenu);
    }
}
