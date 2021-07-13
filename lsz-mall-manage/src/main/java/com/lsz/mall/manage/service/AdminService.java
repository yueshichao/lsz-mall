package com.lsz.mall.manage.service;

import com.lsz.mall.base.entity.Admin;
import com.lsz.mall.base.entity.AdminParam;
import com.lsz.mall.base.entity.UmsRole;
import com.lsz.mall.base.entity.UpdateAdminPasswordParam;
import com.lsz.mall.base.vo.CommonPage;

import java.util.List;

public interface AdminService {


    Admin register(AdminParam umsAdminParam);

    String login(String username, String password);

    String refreshToken(String token);

    Admin getAdminByUsername(String username);

    List<UmsRole> getRoleList(Long id);

    CommonPage<Admin> getPage(String keyword, Integer pageSize, Integer pageNum);

    Admin getItem(Long id);

    int update(Long id, Admin admin);

    int updatePassword(UpdateAdminPasswordParam updatePasswordParam);

    int delete(Long id);

    int updateRole(Long adminId, List<Long> roleIds);
}
