package com.lsz.mall.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class AdminParam {

    @NotEmpty
    @ApiModelProperty(value = "用户名", required = true)
    protected String username;

    @NotEmpty
    @ApiModelProperty(value = "密码", required = true)
    protected String password;

    @ApiModelProperty(value = "用户头像")
    protected String icon;

    @Email
    @ApiModelProperty(value = "邮箱")
    protected String email;

    @ApiModelProperty(value = "用户昵称")
    protected String nickName;

    @ApiModelProperty(value = "备注")
    protected String note;

}
