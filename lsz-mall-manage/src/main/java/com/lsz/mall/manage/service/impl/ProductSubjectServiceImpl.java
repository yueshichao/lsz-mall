package com.lsz.mall.manage.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsz.mall.base.entity.ProductSubject;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.manage.dao.ProductSubjectDao;
import com.lsz.mall.manage.service.ProductSubjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ProductSubjectServiceImpl implements ProductSubjectService {

    @Autowired
    ProductSubjectDao subjectDao;

    @Override
    public List<ProductSubject> listAll() {
        return subjectDao.selectList(null);
    }

    @Override
    public CommonPage<ProductSubject> getPage(String keyword, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<ProductSubject> wrapper = new LambdaQueryWrapper<ProductSubject>()
                .eq(StrUtil.isNotBlank(keyword), ProductSubject::getTitle, keyword);
        IPage<ProductSubject> page = subjectDao.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return CommonPage.restPage(page);
    }
}
