package com.lsz.mall.manage.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsz.mall.base.entity.Coupon;
import com.lsz.mall.base.entity.CouponParam;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.manage.dao.CouponDao;
import com.lsz.mall.manage.dao.CouponProductCategoryRelationDao;
import com.lsz.mall.manage.dao.CouponProductRelationDao;
import com.lsz.mall.manage.service.CouponService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    CouponDao couponDao;

    @Autowired
    CouponProductRelationDao couponProductRelationDao;

    @Autowired
    CouponProductCategoryRelationDao couponProductCategoryRelationDao;


    @Override
    public int create(CouponParam couponParam) {
        Coupon coupon = new Coupon();
        BeanUtils.copyProperties(couponParam, coupon);
        // 初始化值
        coupon.setCount(coupon.getPublishCount());
        coupon.setUseCount(0);
        coupon.setReceiveCount(0);
        int count = couponDao.insert(coupon);

        Long couponId = coupon.getId();
        Integer useType = coupon.getUseType();

        if (useType == Coupon.UseType.USE_TYPE_CATEGORY.getValue()) {
            // 商品类型与优惠券关系
            Optional.ofNullable(couponParam.getProductCategoryRelationList())
                    .ifPresent(list -> {
                        list.forEach(r -> {
                            r.setCouponId(couponId);
                            couponProductCategoryRelationDao.insert(r);
                        });
                    });
        } else if (useType == Coupon.UseType.USE_TYPE_PRODUCT.getValue()) {
            // 商品与优惠券关系
            Optional.ofNullable(couponParam.getProductRelationList())
                    .ifPresent(list -> {
                        list.forEach(r -> {
                            r.setCouponId(couponId);
                            // 可以优化成批量插入
                            couponProductRelationDao.insert(r);
                        });
                    });
        }

        return count;
    }

    @Override
    public CommonPage<Coupon> getPage(String keyword, Integer type, Integer pageSize, Integer pageNum) {

        QueryWrapper<Coupon> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(type != null, Coupon::getType, type)
                .like(StrUtil.isNotBlank(keyword), Coupon::getName, keyword);

        IPage<Coupon> page = couponDao.selectPage(new Page<>(pageNum, pageSize), queryWrapper);
        return CommonPage.restPage(page);
    }
}
