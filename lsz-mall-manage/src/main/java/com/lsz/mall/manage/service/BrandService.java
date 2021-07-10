package com.lsz.mall.manage.service;

import com.lsz.mall.base.entity.Brand;
import com.lsz.mall.base.entity.BrandParam;
import com.lsz.mall.base.vo.CommonPage;

public interface BrandService {

    CommonPage<Brand> getBrandPage(String keyword, Integer pageNum, Integer pageSize);

    int createBrand(BrandParam brandParam);

}
