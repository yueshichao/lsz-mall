package com.lsz.mall.manage.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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

import java.util.List;
import java.util.function.Consumer;

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

    @Override
    public List<Brand> listAllBrand() {
        return brandDao.selectList(null);
    }

    @Override
    public int updateBrand(Long id, BrandParam brandParam) {
        Brand brand = new Brand();
        BeanUtils.copyProperties(brandParam, brand);
        brand.setId(id);
        return brandDao.updateById(brand);
    }

    @Override
    public int deleteBrand(Long id) {
        return brandDao.deleteById(id);
    }

    @Override
    public Brand getBrand(Long id) {
        return brandDao.selectById(id);
    }

    @Override
    public int deleteBrand(List<Long> ids) {
        return brandDao.deleteBatchIds(ids);
    }

    @Override
    public int updateShowStatus(List<Long> ids, Integer showStatus) {
        int count = updateByIds(ids, brand -> brand.setShowStatus(showStatus));
        return count;
    }

    private int updateByIds(List<Long> ids, Consumer<Brand> brandConsumer) {
        LambdaQueryWrapper<Brand> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Brand::getId, ids);

        Brand brand = new Brand();
        brandConsumer.accept(brand);
        int count = brandDao.update(brand, wrapper);
        return count;
    }


    @Override
    public int updateFactoryStatus(List<Long> ids, Integer factoryStatus) {
        int count = updateByIds(ids, brand -> brand.setFactoryStatus(factoryStatus));
        return count;
    }
}
