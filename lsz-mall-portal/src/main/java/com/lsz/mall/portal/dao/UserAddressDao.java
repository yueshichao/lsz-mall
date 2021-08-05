package com.lsz.mall.portal.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsz.mall.base.entity.CartItem;
import com.lsz.mall.base.entity.UserAddress;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface UserAddressDao extends BaseMapper<UserAddress> {


}
