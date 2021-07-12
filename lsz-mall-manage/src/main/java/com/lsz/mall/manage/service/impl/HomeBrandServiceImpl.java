package com.lsz.mall.manage.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsz.mall.base.entity.HomeBrand;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.manage.dao.HomeBrandDao;
import com.lsz.mall.manage.service.HomeBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeBrandServiceImpl implements HomeBrandService {

    @Autowired
    private HomeBrandDao homeBrandDao;

    @Override
    public int create(List<HomeBrand> homeBrandList) {
        int count = homeBrandList.stream()
                // peek本质接收一个consumer，一般用于debug
                // 参考：https://www.imooc.com/article/details/id/38056
                .map(homeBrand -> {
                    homeBrand.setRecommendStatus(1);
                    homeBrand.setSort(0);
                    return homeBrand;
                })
                .map(homeBrand -> homeBrandDao.insert(homeBrand))
                .mapToInt(i -> i)
                .sum();
        return count;
    }

    @Override
    public CommonPage<HomeBrand> list(String brandName, Integer recommendStatus, Integer pageSize, Integer pageNum) {
        QueryWrapper<HomeBrand> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(recommendStatus != null, HomeBrand::getRecommendStatus, recommendStatus)
                .like(StrUtil.isNotBlank(brandName), HomeBrand::getBrandName, brandName);
        IPage<HomeBrand> page = homeBrandDao.selectPage(new Page<>(pageNum, pageSize), queryWrapper);
        return CommonPage.restPage(page);
    }
}
