package com.lsz.mall.manage.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsz.mall.base.entity.HomeAdvertise;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.manage.dao.HomeAdvertiseDao;
import com.lsz.mall.manage.service.HomeAdvertiseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class HomeAdvertiseServiceImpl implements HomeAdvertiseService {

    @Autowired
    HomeAdvertiseDao homeAdvertiseDao;

    @Override
    public int create(HomeAdvertise advertise) {
        advertise.setClickCount(0);
        advertise.setOrderCount(0);
        return homeAdvertiseDao.insert(advertise);
    }

    @Override
    public int delete(List<Long> ids) {
        return homeAdvertiseDao.deleteBatchIds(ids);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        LambdaQueryWrapper<HomeAdvertise> wrapper = new LambdaQueryWrapper<HomeAdvertise>()
                .eq(HomeAdvertise::getId, id);

        HomeAdvertise homeAdvertise = new HomeAdvertise();
        homeAdvertise.setStatus(status);
        return homeAdvertiseDao.update(homeAdvertise, wrapper);
    }

    @Override
    public HomeAdvertise getItem(Long id) {
        return homeAdvertiseDao.selectById(id);
    }

    @Override
    public int update(Long id, HomeAdvertise advertise) {
        advertise.setId(id);
        return homeAdvertiseDao.updateById(advertise);
    }

    @Override
    public CommonPage<HomeAdvertise> list(String name, Integer type, String endTimeStr, Integer pageSize, Integer pageNum) {
        LambdaQueryWrapper<HomeAdvertise> wrapper = new LambdaQueryWrapper<HomeAdvertise>()
                .like(StrUtil.isNotBlank(name), HomeAdvertise::getId, name)
                .eq(type != null, HomeAdvertise::getType, type);

        if (StrUtil.isNotBlank(endTimeStr)) {
            Date endTime = DateUtil.parse(endTimeStr).toJdkDate();
            wrapper.le(HomeAdvertise::getEndTime, endTime);
        }

        IPage<HomeAdvertise> page = homeAdvertiseDao.selectPage(new Page<>(pageNum, pageSize), wrapper);

        return CommonPage.restPage(page);
    }
}
