package com.lsz.mall.base.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

/*
{
    "id": 6,
    "name": "小米",
    "firstLetter": "M",
    "sort": 500,
    "factoryStatus": 1,
    "showStatus": 1,
    "productCount": 100,
    "productCommentCount": 100,
    "logo": "http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180518/5a912944N474afb7a.png",
    "bigPic": "http://macro-oss.oss-cn-shenzhen.aliyuncs.com/mall/images/20180518/5afd7778Nf7800b75.jpg",
    "brandStory": "小米手机的故事"
}
* */
@Data
public class BrandParam {
    
    @NotEmpty
    @ApiModelProperty(value = "品牌名称", required = true)
    protected String name;
    
    @ApiModelProperty(value = "品牌首字母")
    protected String firstLetter;
    
    @Min(value = 0)
    @ApiModelProperty(value = "排序字段")
    protected Integer sort;
    
    @ApiModelProperty(value = "是否为厂家制造商")
    protected Integer factoryStatus;
    
    @ApiModelProperty(value = "是否进行显示")
    protected Integer showStatus;
    
    @NotEmpty
    @ApiModelProperty(value = "品牌logo", required = true)
    protected String logo;
    
    @ApiModelProperty(value = "品牌大图")
    protected String bigPic;
    
    @ApiModelProperty(value = "品牌故事")
    protected String brandStory;
    
}
