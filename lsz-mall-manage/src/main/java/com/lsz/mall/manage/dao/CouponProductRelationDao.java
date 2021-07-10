package com.lsz.mall.manage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsz.mall.base.entity.CouponProductRelation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface CouponProductRelationDao extends BaseMapper<CouponProductRelation> {


}
