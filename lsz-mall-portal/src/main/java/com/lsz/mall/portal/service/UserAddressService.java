package com.lsz.mall.portal.service;

import com.lsz.mall.portal.entity.SaveAddressParam;
import com.lsz.mall.portal.entity.UserAddressVO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserAddressService {


    List<UserAddressVO> findAll();

    @Transactional
    int save(SaveAddressParam saveAddressParam);

    @Transactional
    int update(UserAddressVO updateAddressParam);

    UserAddressVO get(Long addressId);

    UserAddressVO getDefaultAddress();

    @Transactional
    int delete(Long addressId);


}
