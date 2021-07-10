package com.lsz.mall.manage.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsz.mall.base.entity.Brand;
import com.lsz.mall.base.entity.BrandParam;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.manage.dao.BrandDao;
import com.lsz.mall.manage.service.BrandService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    BrandDao brandDao;

    @Override
    public CommonPage<Brand> getBrandPage(String keyword, Integer pageNum, Integer pageSize) {
        QueryWrapper<Brand> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().like(StrUtil.isNotBlank(keyword), BrandParam::getName, keyword).orderByDesc(BrandParam::getSort);
        IPage<Brand> page = brandDao.selectPage(new Page<>(pageNum, pageSize), queryWrapper);
        return CommonPage.restPage(page);
    }

    @Override
    public int createBrand(BrandParam brandParam) {
        Brand brand = new Brand();
        BeanUtils.copyProperties(brandParam, brand);
        if (StrUtil.isEmpty(brand.getFirstLetter())) {
            brand.setFirstLetter(brand.getName().substring(0, 1));
        }
        return brandDao.insert(brand);
    }
}
