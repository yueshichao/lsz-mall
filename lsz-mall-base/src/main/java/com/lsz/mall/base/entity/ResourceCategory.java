package com.lsz.mall.base.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("ums_resource_category")
public class ResourceCategory {

    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "分类名称")
    private String name;

    @ApiModelProperty(value = "排序")
    private Integer sort;

}