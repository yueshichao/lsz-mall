package com.lsz.mall.portal.service;

import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.portal.entity.SaveCartItemParam;
import com.lsz.mall.portal.entity.ShoppingCartItemVO;
import com.lsz.mall.portal.entity.UpdateCartItemParam;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ShoppingCartService {

    CommonPage<ShoppingCartItemVO> getPage(Integer pageNumber, Integer pageSize);

    List<ShoppingCartItemVO> getList();

    int saveItem(SaveCartItemParam saveCartItemParam);

    @Transactional
    int updateItem(UpdateCartItemParam updateCartItemParam);

    @Transactional
    int delete(Long shoppingCartId);

    List<ShoppingCartItemVO> getDetail(List<Long> cartItemIds);

}
