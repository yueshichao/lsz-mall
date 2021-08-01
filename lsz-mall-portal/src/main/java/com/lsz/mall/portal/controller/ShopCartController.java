package com.lsz.mall.portal.controller;

import com.lsz.mall.base.entity.Res;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.portal.entity.SaveCartItemParam;
import com.lsz.mall.portal.entity.ShoppingCartItemVO;
import com.lsz.mall.portal.entity.UpdateCartItemParam;
import com.lsz.mall.portal.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(tags = "购物车")
@RequestMapping("/api/v1")
@Slf4j
public class ShopCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/shop-cart/page")
    @ApiOperation(value = "购物车列表(每页默认5条)")
    public Res<CommonPage<ShoppingCartItemVO>> cartItemPageList(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        CommonPage<ShoppingCartItemVO> page = shoppingCartService.getPage(pageNumber, pageSize);
        return Res.ok(page);
    }

    @GetMapping("/shop-cart")
    @ApiOperation(value = "购物车列表(网页移动端不分页)")
    public Res<List<ShoppingCartItemVO>> cartItemList() {
        List<ShoppingCartItemVO> list = shoppingCartService.getList();
        return Res.ok(list);
    }

    @PostMapping("/shop-cart")
    @ApiOperation(value = "添加商品到购物车接口")
    public Res saveShoppingCartItem(@Validated @RequestBody SaveCartItemParam saveCartItemParam) {
        int count = shoppingCartService.saveItem(saveCartItemParam);
        return Res.ok(count);
    }

    @PutMapping("/shop-cart")
    @ApiOperation(value = "修改购物项数据")
    public Res updateShoppingCartItem(@RequestBody UpdateCartItemParam updateCartItemParam) {
        int count = shoppingCartService.updateItem(updateCartItemParam);
        return Res.ok(count);
    }

    @DeleteMapping("/shop-cart/{shoppingCartId}")
    @ApiOperation(value = "删除购物项")
    public Res updateShoppingCartItem(@PathVariable("shoppingCartId") Long shoppingCartId) {
        int count = shoppingCartService.delete(shoppingCartId);
        return Res.ok(count);
    }

    @GetMapping("/shop-cart/settle")
    @ApiOperation(value = "根据购物项id查询购物项明细")
    public Res<List<ShoppingCartItemVO>> getDetail(@RequestParam List<Long> cartItemIds) {
        List<ShoppingCartItemVO> list = shoppingCartService.getDetail(cartItemIds);
        return Res.ok(list);
    }

}
