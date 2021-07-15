package com.lsz.mall.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lsz.mall.base.entity.MemberLevel;
import com.lsz.mall.manage.dao.MemberLevelDao;
import com.lsz.mall.manage.service.MemberLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberLevelServiceImpl implements MemberLevelService {

    @Autowired
    MemberLevelDao memberLevelDao;

    @Override
    public List<MemberLevel> list(Integer defaultStatus) {
        LambdaQueryWrapper<MemberLevel> wrapper = new LambdaQueryWrapper<MemberLevel>()
                .eq(MemberLevel::getDefaultStatus, defaultStatus);
        return memberLevelDao.selectList(wrapper);
    }
}
