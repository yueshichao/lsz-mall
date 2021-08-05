package com.lsz.mall.manage.service;

import com.lsz.mall.base.entity.Brand;
import com.lsz.mall.base.entity.BrandParam;
import com.lsz.mall.base.vo.CommonPage;

import java.util.List;

public interface BrandService {

    CommonPage<Brand> getBrandPage(String keyword, Integer pageNum, Integer pageSize);

    int createBrand(BrandParam brandParam);

    List<Brand> listAllBrand();

    int updateBrand(Long id, BrandParam brandParam);

    int deleteBrand(Long id);

    Brand getBrand(Long id);

    int deleteBrand(List<Long> ids);

    int updateShowStatus(List<Long> ids, Integer showStatus);

    int updateFactoryStatus(List<Long> ids, Integer factoryStatus);

}
