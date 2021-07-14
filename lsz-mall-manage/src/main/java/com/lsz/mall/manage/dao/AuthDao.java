package com.lsz.mall.manage.dao;

import com.lsz.mall.base.entity.AdminMenu;
import com.lsz.mall.base.entity.AdminResource;
import com.lsz.mall.base.entity.AdminRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AuthDao {

    /**
     * 获取用户所有可访问资源
     */
    List<AdminResource> getResourceList(@Param("adminId") Long adminId);


    /**
     * 获取用于所有角色
     */
    List<AdminRole> getRoleList(@Param("adminId") Long adminId);

    /**
     * 根据后台用户ID获取菜单
     */
    List<AdminMenu> getMenuList(@Param("adminId") Long adminId);

    /**
     * 根据角色ID获取菜单
     */
    List<AdminMenu> getMenuListByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据角色ID获取资源
     */
    List<AdminResource> getResourceListByRoleId(@Param("roleId") Long roleId);

}
