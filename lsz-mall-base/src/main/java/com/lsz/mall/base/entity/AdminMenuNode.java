package com.lsz.mall.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class AdminMenuNode extends AdminMenu {

    @ApiModelProperty(value = "子级菜单")
    private List<AdminMenuNode> children;

    public AdminMenuNode(AdminMenu p) {
        super(p);
    }
}
