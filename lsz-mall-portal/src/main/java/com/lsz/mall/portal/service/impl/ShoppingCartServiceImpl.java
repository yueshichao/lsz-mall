package com.lsz.mall.portal.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsz.mall.base.entity.CartItem;
import com.lsz.mall.base.entity.Member;
import com.lsz.mall.base.entity.Product;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.portal.dao.CartItemDao;
import com.lsz.mall.portal.dao.ProductDao;
import com.lsz.mall.portal.entity.SaveCartItemParam;
import com.lsz.mall.portal.entity.ShoppingCartItemVO;
import com.lsz.mall.portal.entity.UpdateCartItemParam;
import com.lsz.mall.portal.service.ShoppingCartService;
import com.lsz.mall.portal.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    CartItemDao cartItemDao;

    @Autowired
    UserService userService;

    @Autowired
    ProductDao productDao;

    @Override
    public CommonPage<ShoppingCartItemVO> getPage(Integer pageNumber, Integer pageSize) {
        Member currentMember = userService.getCurrentMember();
        LambdaQueryWrapper<CartItem> wrapper = new LambdaQueryWrapper<CartItem>()
                .eq(CartItem::getMemberId, currentMember.getId());

        IPage<CartItem> page = cartItemDao.selectPage(new Page<>(pageNumber, pageSize), wrapper);
        List<ShoppingCartItemVO> list = Optional.ofNullable(page.getRecords())
                .orElseGet(ArrayList::new)
                .stream()
                .map(r -> new ShoppingCartItemVO(r))
                .collect(Collectors.toList());
        return CommonPage.restPage(page, list);
    }

    @Override
    public List<ShoppingCartItemVO> getList() {
        Member currentMember = userService.getCurrentMember();
        LambdaQueryWrapper<CartItem> wrapper = new LambdaQueryWrapper<CartItem>()
                .eq(CartItem::getMemberId, currentMember.getId());
        List<CartItem> cartItems = cartItemDao.selectList(wrapper);
        List<ShoppingCartItemVO> list = Optional.ofNullable(cartItems)
                .orElseGet(ArrayList::new)
                .stream()
                .map(r -> new ShoppingCartItemVO(r))
                .collect(Collectors.toList());
        return list;
    }

    @Override
    public int saveItem(SaveCartItemParam saveCartItemParam) {
        Long productId = saveCartItemParam.getGoodsId();
        Product product = productDao.selectById(productId);

        CartItem cartItem = new CartItem();
        Member currentMember = userService.getCurrentMember();
        cartItem.setMemberId(currentMember.getId());
        cartItem.setProductId(productId);
        cartItem.setProductName(product.getName());
        cartItem.setPrice(product.getPrice());
        cartItem.setQuantity(saveCartItemParam.getGoodsCount());
        cartItem.setCreateDate(new Date());
        log.debug("userName = {}, 购物车添加物品 = {}", currentMember.getUsername(), JSON.toJSONString(cartItem));
        return cartItemDao.insert(cartItem);
    }

    @Override
    public int updateItem(UpdateCartItemParam updateCartItemParam) {
        Long userId = userService.getCurrentMember().getId();
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(updateCartItemParam.getGoodsCount());

        LambdaQueryWrapper<CartItem> wrapper = new LambdaQueryWrapper<CartItem>()
                .eq(CartItem::getMemberId, userId)
                .eq(CartItem::getId, updateCartItemParam.getCartItemId());

        log.debug("userId = {}, 购物车更新，cartId = {}, after count = {}", userId, updateCartItemParam.getCartItemId(), updateCartItemParam.getGoodsCount());
        return cartItemDao.update(cartItem, wrapper);
    }

    @Override
    public int delete(Long shoppingCartId) {
        return cartItemDao.deleteById(shoppingCartId);
    }

    @Override
    public List<ShoppingCartItemVO> getDetail(List<Long> cartItemIds) {
        return null;
    }

}
