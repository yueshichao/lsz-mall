package com.lsz.mall.portal.entity;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lsz.mall.base.entity.SkuSpData;
import com.lsz.mall.base.entity.SkuStock;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class SkuStockVO {

    private Long id;

    private Long productId;

    @ApiModelProperty(value = "sku编码")
    private String skuCode;

    private BigDecimal price;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(value = "展示图片")
    private String pic;

    @ApiModelProperty(value = "具体款式")
    private String shapeName;

    public SkuStockVO(SkuStock skuStock) {
        this.id = skuStock.getId();
        this.productId = skuStock.getProductId();
        this.skuCode = skuStock.getSkuCode();
        this.price = skuStock.getPrice();
        this.stock = skuStock.getStock() - skuStock.getLockStock();
        this.pic = skuStock.getPic();
        List<SkuSpData> skuSpData = JSON.parseArray(skuStock.getSpData(), SkuSpData.class);
        String shapeName = skuSpData.stream()
                .map(s -> StrUtil.format("{}:{}", s.getKey(), s.getValue()))
                .collect(Collectors.joining(";"));
        this.shapeName = shapeName;
    }
}