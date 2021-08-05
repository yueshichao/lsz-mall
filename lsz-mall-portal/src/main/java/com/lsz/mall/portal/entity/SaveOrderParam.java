package com.lsz.mall.portal.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class SaveOrderParam {

    @ApiModelProperty("订单项id列表")
    @NotEmpty
    private List<Long> cartItemIds;

    @ApiModelProperty("地址id")
    @NotNull
    private Long addressId;

}
