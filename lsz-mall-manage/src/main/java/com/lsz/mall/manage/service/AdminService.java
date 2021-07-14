package com.lsz.mall.manage.service;

import com.lsz.mall.base.entity.Admin;
import com.lsz.mall.base.entity.AdminParam;
import com.lsz.mall.base.entity.AdminRole;
import com.lsz.mall.base.entity.UpdateAdminPasswordParam;
import com.lsz.mall.base.vo.CommonPage;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AdminService {


    Admin register(AdminParam umsAdminParam);

    String login(String username, String password);

    String refreshToken(String token);

    Admin getAdminByUsername(String username);

    Admin getAdminByToken(String token);

    List<AdminRole> getRoleList(Long adminId);

    CommonPage<Admin> getPage(String keyword, Integer pageSize, Integer pageNum);

    Admin getItem(Long id);

    int update(Long id, Admin admin);

    int updatePassword(UpdateAdminPasswordParam updatePasswordParam);

    int delete(Long id);

    @Transactional
    int updateRole(Long adminId, List<Long> roleIds);

}
