package com.lsz.mall.portal.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lsz.mall.base.entity.Member;
import com.lsz.mall.base.entity.UserAddress;
import com.lsz.mall.portal.dao.UserAddressDao;
import com.lsz.mall.portal.entity.SaveAddressParam;
import com.lsz.mall.portal.entity.UserAddressVO;
import com.lsz.mall.portal.service.UserAddressService;
import com.lsz.mall.portal.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AddressServiceImpl implements UserAddressService {


    @Autowired
    UserAddressDao userAddressDao;

    @Autowired
    UserService userService;

    @Override
    public List<UserAddressVO> findAll() {
        Member currentMember = userService.getCurrentMember();
        LambdaQueryWrapper<UserAddress> wrapper = new LambdaQueryWrapper<UserAddress>()
                .eq(UserAddress::getMemberId, currentMember.getId());
        List<UserAddress> userAddresses = userAddressDao.selectList(wrapper);
        List<UserAddressVO> result = Optional.ofNullable(userAddresses)
                .orElseGet(ArrayList::new)
                .stream()
                .map(a -> new UserAddressVO(a))
                .collect(Collectors.toList());
        return result;
    }

    @Override
    public int save(SaveAddressParam saveAddressParam) {
        Member currentMember = userService.getCurrentMember();

        UserAddress userAddress = new UserAddress();
        userAddress.setMemberId(currentMember.getId());

        fillUserAddress(userAddress, saveAddressParam);
        log.debug("save param = {}, userId = {}", JSON.toJSONString(saveAddressParam), currentMember.getId());

        return userAddressDao.insert(userAddress);
    }

    private void fillUserAddress(UserAddress userAddress, SaveAddressParam addressParam) {
        userAddress.setName(addressParam.getUserName());
        userAddress.setPhoneNumber(addressParam.getUserPhone());
        userAddress.setDefaultStatus(addressParam.getDefaultFlag());

//        userAddress.setPostCode();
        userAddress.setProvince(addressParam.getProvinceName());
        userAddress.setCity(addressParam.getCityName());
        userAddress.setRegion(addressParam.getRegionName());
        userAddress.setDetailAddress(addressParam.getDetailAddress());
    }

    @Override
    public int update(UserAddressVO updateAddressParam) {
        Member currentMember = userService.getCurrentMember();

        UserAddress userAddress = new UserAddress();
        userAddress.setMemberId(currentMember.getId());
        userAddress.setId(updateAddressParam.getAddressId());
        fillUserAddress(userAddress, updateAddressParam);
        log.debug("update addressId = {}, userId = {}", updateAddressParam.getAddressId(), currentMember.getId());
        return userAddressDao.updateById(userAddress);
    }

    @Override
    public UserAddressVO get(Long addressId) {
        UserAddress userAddress = userAddressDao.selectById(addressId);
        UserAddressVO userAddressVO = new UserAddressVO(userAddress);
        return userAddressVO;
    }

    @Override
    public UserAddressVO getDefaultAddress() {
        Member currentMember = userService.getCurrentMember();

        LambdaQueryWrapper<UserAddress> wrapper = new LambdaQueryWrapper<UserAddress>()
                .eq(UserAddress::getMemberId, currentMember.getId())
                .eq(UserAddress::getDefaultStatus, 1);

        UserAddress userAddress = userAddressDao.selectOne(wrapper);
        return Optional.ofNullable(userAddress)
                .map(a -> new UserAddressVO(a))
                .orElse(null);
//        return new UserAddressVO(userAddress);
    }

    @Override
    public int delete(Long addressId) {
        Member currentMember = userService.getCurrentMember();
        LambdaQueryWrapper<UserAddress> wrapper = new LambdaQueryWrapper<UserAddress>()
                .eq(UserAddress::getMemberId, currentMember.getId())
                .eq(UserAddress::getId, addressId);

        log.debug("delete addressId = {}, userId = {}", addressId, currentMember.getId());
        int count = userAddressDao.delete(wrapper);

        return count;
    }
}
