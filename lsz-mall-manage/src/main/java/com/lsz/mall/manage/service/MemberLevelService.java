package com.lsz.mall.manage.service;


import com.lsz.mall.base.entity.MemberLevel;

import java.util.List;

public interface MemberLevelService {


    List<MemberLevel> list(Integer defaultStatus);

}
