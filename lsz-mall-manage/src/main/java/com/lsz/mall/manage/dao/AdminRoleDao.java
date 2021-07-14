package com.lsz.mall.manage.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lsz.mall.base.entity.AdminRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface AdminRoleDao extends BaseMapper<AdminRole> {

}
