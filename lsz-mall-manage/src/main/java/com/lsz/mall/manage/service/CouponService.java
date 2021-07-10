package com.lsz.mall.manage.service;

import com.lsz.mall.base.entity.Coupon;
import com.lsz.mall.base.entity.CouponParam;
import com.lsz.mall.base.vo.CommonPage;

public interface CouponService {


    int create(CouponParam couponParam);

    CommonPage<Coupon> getPage(String keyword, Integer type, Integer pageSize, Integer pageNum);

}
