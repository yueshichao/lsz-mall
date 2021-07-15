package com.lsz.mall.manage.service.impl;

import com.lsz.mall.base.entity.PreferenceArea;
import com.lsz.mall.manage.dao.PreferenceAreaDao;
import com.lsz.mall.manage.service.PreferenceAreaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class PreferenceAreaServiceImpl implements PreferenceAreaService {

    @Autowired
    PreferenceAreaDao preferenceAreaDao;

    @Override
    public List<PreferenceArea> listAll() {
        return preferenceAreaDao.selectList(null);
    }
}
