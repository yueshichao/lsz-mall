package com.lsz.mall.base.param;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateUserInfoParam extends UserInfoParam {

    private String password;

}
