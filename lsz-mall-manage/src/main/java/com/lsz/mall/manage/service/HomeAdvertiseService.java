package com.lsz.mall.manage.service;

import com.lsz.mall.base.entity.HomeAdvertise;
import com.lsz.mall.base.vo.CommonPage;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface HomeAdvertiseService {

    @Transactional
    int create(HomeAdvertise advertise);

    @Transactional
    int delete(List<Long> ids);

    @Transactional
    int updateStatus(Long id, Integer status);

    HomeAdvertise getItem(Long id);

    @Transactional
    int update(Long id, HomeAdvertise advertise);

    CommonPage<HomeAdvertise> list(String name, Integer type, String endTimeStr, Integer pageSize, Integer pageNum);

}
