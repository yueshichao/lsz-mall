package com.lsz.mall.portal.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lsz.mall.base.entity.*;
import com.lsz.mall.base.vo.CommonPage;
import com.lsz.mall.portal.dao.*;
import com.lsz.mall.portal.entity.OrderDetailVO;
import com.lsz.mall.portal.entity.OrderItemVO;
import com.lsz.mall.portal.entity.OrderListVO;
import com.lsz.mall.portal.entity.SaveOrderParam;
import com.lsz.mall.portal.service.IdService;
import com.lsz.mall.portal.service.OrderService;
import com.lsz.mall.portal.service.ShoppingCartService;
import com.lsz.mall.portal.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDao orderDao;

    @Autowired
    CartItemDao cartItemDao;

    @Autowired
    UserAddressDao userAddressDao;

    @Autowired
    UserService userService;

    @Autowired
    ShoppingCartService shoppingCartService;

    @Autowired
    IdService idService;

    @Autowired
    OrderItemDao orderItemDao;

    @Autowired
    ProductDao productDao;

    @Override
    public String saveOrder(SaveOrderParam saveOrderParam) {
        Member currentMember = userService.getCurrentMember();

        log.debug("userId = {}, save order param = {}", currentMember.getId(), JSON.toJSONString(saveOrderParam));

        List<Long> cartItemIds = saveOrderParam.getCartItemIds();
        Long addressId = saveOrderParam.getAddressId();


        UserAddress userAddress = userAddressDao.selectById(addressId);

        List<CartItem> cartItems = cartItemDao.selectBatchIds(cartItemIds);

        // 收货地址，计算价格
        Order order = generate(currentMember, userAddress, cartItems);
        order.setCreateTime(new Date());
        // 0->未支付；1->支付宝；2->微信
        order.setPayType(1);
        // 0->PC订单；1->app订单
        order.setSourceType(1);
        // 0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单
        order.setStatus(0);
        // 0->正常订单，1->秒杀订单
        order.setOrderType(0);

        // 积分
        order.setIntegration(0);
        // 成长值
        order.setGrowth(0);

        // ID从id服务来
        Long orderId = idService.generateOrderId();
        order.setId(orderId);
        // TODO
        String orderSn = orderId + "";
        order.setOrderSn(orderSn);

        // 自动收货
        order.setAutoConfirmDay(15);

        // TODO 扣除积分、优惠券

        // 在购物车上删除已购买的商品
        cartItemDao.deleteBatchIds(saveOrderParam.getCartItemIds());

        // TODO 未及时支付，延迟队列取消订单

        orderDao.insert(order);


        // 订单关联商品
        // 购物车属性赋值
        List<OrderItem> orderItems = cartItems.stream()
                .map(c -> {
                    OrderItem orderItem = new OrderItem(c);
                    orderItem.setOrderId(order.getId());
                    orderItem.setOrderSn(order.getOrderSn());
                    return orderItem;
                })
                .collect(Collectors.toList());
        // 查询商品属性：促销、积分、成长值
        Set<Long> productIds = cartItems.stream().map(CartItem::getProductId).collect(Collectors.toSet());
        Map<Long, Product> productId2Product = productDao.selectBatchIds(productIds)
                .stream()
                .collect(Collectors.toMap(p -> p.getId(), p -> p));
        for (OrderItem orderItem : orderItems) {
            Long productId = orderItem.getProductId();
            Product product = productId2Product.get(productId);

            // TODO 优惠力度
//            BigDecimal promotionPrice = product.getPromotionPrice();
//            orderItem.setPromotionAmount(promotionPrice.multiply(new BigDecimal(orderItem.getProductQuantity())));

            orderItem.setProductPrice(product.getPrice().multiply(new BigDecimal(orderItem.getProductQuantity())));

            orderItemDao.insert(orderItem);
        }


        log.debug("userId = {}, order = {}", currentMember.getId(), JSON.toJSONString(order));
        log.info("userId = {}, 订单编号 = {}", currentMember.getId(), orderSn);
        return orderSn;
    }

    private Order generate(Member currentMember, UserAddress userAddress, List<CartItem> cartItems) {
        Order order = new Order();

        // 接收人信息
        order.setReceiverProvince(userAddress.getProvince());
        order.setReceiverCity(userAddress.getCity());
        order.setReceiverRegion(userAddress.getRegion());
        order.setReceiverDetailAddress(userAddress.getDetailAddress());
        order.setReceiverName(userAddress.getName());
        order.setReceiverPhone(userAddress.getPhoneNumber());
        order.setReceiverPostCode(userAddress.getPostCode());
        order.setMemberId(currentMember.getId());
        order.setMemberUsername(currentMember.getUsername());

        BigDecimal totalAmount = new BigDecimal("0");
        BigDecimal freightAmount = new BigDecimal("0");
        BigDecimal promotionAmount = new BigDecimal("0");
        BigDecimal integrationAmount = new BigDecimal("0");
        BigDecimal couponAmount = new BigDecimal("0");
        // 计算总价，运费，促销，积分抵扣，优惠券金额
        for (CartItem cartItem : cartItems) {
            BigDecimal singlePrice = cartItem.getPrice().multiply(new BigDecimal(cartItem.getQuantity()));
            totalAmount = totalAmount.add(singlePrice);
            freightAmount = freightAmount.add(new BigDecimal(0));
            promotionAmount = promotionAmount.add(new BigDecimal(0));
            integrationAmount = integrationAmount.add(new BigDecimal(0));
            couponAmount = couponAmount.add(new BigDecimal(0));
        }
        order.setTotalAmount(totalAmount);
        order.setFreightAmount(freightAmount);
        order.setPromotionAmount(promotionAmount);
        order.setIntegrationAmount(integrationAmount);
        order.setCouponAmount(couponAmount);
        // 实际应付 = 商品总额 + 运费 - 促销金额 - 积分抵扣 - 优惠券
        BigDecimal payAmount = totalAmount.add(freightAmount)
                .subtract(promotionAmount)
                .subtract(integrationAmount)
                .subtract(couponAmount);
        order.setPayAmount(payAmount);


        return order;

    }

    @Override
    public OrderDetailVO getDetail(String orderId) {
        Order order = orderDao.selectById(orderId);
        LambdaQueryWrapper<OrderItem> wrapper = new LambdaQueryWrapper<OrderItem>()
                .eq(OrderItem::getOrderId, orderId);
        List<OrderItem> orderItems = orderItemDao.selectList(wrapper);

        OrderDetailVO result = new OrderDetailVO(order, orderItems);

        return result;
    }

    @Override
    public CommonPage<OrderListVO> getPage(Integer pageNo, Integer pageSize, Integer status) {
        Member currentMember = userService.getCurrentMember();
        log.debug("userId = {}, status = {}", currentMember.getId(), status);
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<Order>()
                .eq(status != null, Order::getStatus, status)
                .eq(Order::getMemberId, currentMember.getId());
        IPage<Order> page = orderDao.selectPage(new Page<>(pageNo, pageSize), wrapper);
        List<OrderListVO> result = Optional.ofNullable(page.getRecords())
                .orElseGet(ArrayList::new)
                .stream()
                .map(o -> {
                    OrderListVO orderListVO = new OrderListVO(o);
                    return orderListVO;
                })
                .collect(Collectors.toList());
        // 查找商品信息
        Set<Long> orderIds = result.stream().map(OrderListVO::getOrderId).collect(Collectors.toSet());
        if (orderIds.isEmpty()) {
            return CommonPage.empty(pageNo, pageSize);
        }
        LambdaQueryWrapper<OrderItem> orderItemWrapper = new LambdaQueryWrapper<OrderItem>()
                .in(OrderItem::getOrderId, orderIds);
        Map<Long, List<OrderItemVO>> orderId2OrderItemVOS = orderItemDao.selectList(orderItemWrapper)
                .stream()
//                .map(o -> new OrderItemVO(o))
                .collect(Collectors.groupingBy(
                        OrderItem::getOrderId,
                        Collectors.mapping(p -> new OrderItemVO(p), Collectors.toList()))
                );

        for (OrderListVO orderListVO : result) {
            Long orderId = orderListVO.getOrderId();
            List<OrderItemVO> vos = orderId2OrderItemVOS.getOrDefault(orderId, Collections.EMPTY_LIST);
            orderListVO.setOrderItems(vos);
        }

        return CommonPage.restPage(page, result);
    }

    @Override
    public int cancelOrder(String orderId) {
        Member currentMember = userService.getCurrentMember();
        log.info("userId = {}, 取消订单, orderId = {}", currentMember.getId(), orderId);
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<Order>()
                .eq(Order::getMemberId, currentMember.getId())
                .eq(Order::getId, orderId);

        Order order = new Order();
        order.setStatus(OrderStatusEnum.INVALID.getCode());
        int count = orderDao.update(order, wrapper);

        return count;
    }

    @Override
    public int confirmReceive(String orderId) {
        Member currentMember = userService.getCurrentMember();
        log.info("userId = {}, 确认收货, orderId = {}", currentMember.getId(), orderId);
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<Order>()
                .eq(Order::getMemberId, currentMember.getId())
                .eq(Order::getId, orderId);

        Order order = new Order();
        order.setStatus(OrderStatusEnum.FINISHED.getCode());
        return orderDao.update(order, wrapper);
    }

    @Override
    public int paySuccess(String orderId, int payType) {
        Member currentMember = userService.getCurrentMember();
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<Order>()
                .eq(Order::getMemberId, currentMember.getId())
                .eq(Order::getId, orderId);
        Order order = new Order();
        order.setStatus(OrderStatusEnum.TO_POST_PRODUCT.getCode());
        order.setPaymentTime(new Date());
        orderDao.update(order, wrapper);
        log.info("userId = {}, 支付成功！", currentMember.getId());
        return 1;
    }


}
