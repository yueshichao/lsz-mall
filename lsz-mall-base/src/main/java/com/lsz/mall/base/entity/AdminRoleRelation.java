package com.lsz.mall.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@TableName("ums_admin_role_relation")
public class AdminRoleRelation {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long adminId;

    private Long roleId;

    public AdminRoleRelation(Long adminId, Long roleId) {
        this.adminId = adminId;
        this.roleId = roleId;
    }
}


