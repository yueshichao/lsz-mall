package com.lsz.mall.manage.service;

import com.lsz.mall.base.entity.HomeBrand;
import com.lsz.mall.base.vo.CommonPage;

import java.util.List;

public interface HomeBrandService {


    int create(List<HomeBrand> homeBrandList);


    CommonPage<HomeBrand> list(String brandName, Integer recommendStatus, Integer pageSize, Integer pageNum);

}
