package com.lsz.mall.base.param;

import com.lsz.mall.base.entity.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserInfoParam {

    @ApiModelProperty("用户昵称")
    protected String nickName;

    @ApiModelProperty("用户密码")
    protected String loginName;

    @ApiModelProperty("个性签名")
    protected String introduceSign;

    public UserInfoParam(Member currentMember) {
        this.nickName = currentMember.getNickname();
        this.loginName = currentMember.getUsername();
        this.introduceSign = currentMember.getPersonalizedSignature();
    }

}
