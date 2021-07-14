package com.lsz.mall.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("ums_role_resource_relation")
public class AdminRoleResourceRelation {

    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @ApiModelProperty(value = "资源ID")
    private Long resourceId;

    public AdminRoleResourceRelation(Long roleId, Long resourceId) {
        this.roleId = roleId;
        this.resourceId = resourceId;
    }
}