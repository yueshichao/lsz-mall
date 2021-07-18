package com.lsz.mall.manage.service.impl;

import com.lsz.mall.base.entity.CompanyAddress;
import com.lsz.mall.manage.dao.CompanyAddressDao;
import com.lsz.mall.manage.service.CompanyAddressService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CompanyAddressServiceImpl implements CompanyAddressService {

    @Autowired
    CompanyAddressDao companyAddressDao;

    @Override
    public List<CompanyAddress> list() {
        return companyAddressDao.selectList(null);
    }

}
