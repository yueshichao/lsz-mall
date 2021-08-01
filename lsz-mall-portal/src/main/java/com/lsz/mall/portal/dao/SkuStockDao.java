package com.lsz.mall.portal.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsz.mall.base.entity.SkuStock;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface SkuStockDao extends BaseMapper<SkuStock> {

    int lockStock(@Param("id") Long id, @Param("count") Integer count);

    int decrTrueStock(@Param("id") Long id, @Param("count") Integer count);

    int incrTrueStock(@Param("id") Long id, @Param("count") Integer count);

}
