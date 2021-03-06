package com.lsz.mall.portal.service.impl;

import cn.hutool.core.util.StrUtil;
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
import com.lsz.mall.portal.service.*;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
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

    @Autowired
    SkuStockDao skuStockDao;

    @Autowired
    SkuStockService skuStockService;

    @Autowired
    OrderSettingDao orderSettingDao;

    @Autowired
    RedissonClient redisson;

    @Resource(name = "orderDelayQueue")
    RDelayedQueue<String> orderDelayQueue;

    @Resource(name = "orderCancelPool")
    ThreadPoolExecutor orderCancelPool;

    @Override
    public String saveOrder(SaveOrderParam saveOrderParam) {
        Member currentMember = userService.getCurrentMember();

        log.debug("userId = {}, save order param = {}", currentMember.getId(), JSON.toJSONString(saveOrderParam));

        List<Long> cartItemIds = saveOrderParam.getCartItemIds();
        Long addressId = saveOrderParam.getAddressId();


        UserAddress userAddress = userAddressDao.selectById(addressId);

        List<CartItem> cartItems = cartItemDao.selectBatchIds(cartItemIds);


        // ?????????
        Map<Long, Integer> productSkuId2Count = cartItems.stream()
                .collect(
                        Collectors.groupingBy(
                                CartItem::getProductSkuId,
                                Collectors.mapping(CartItem::getQuantity, Collectors.summingInt(i -> i))
                        )
                );


        for (Map.Entry<Long, Integer> entry : productSkuId2Count.entrySet()) {
            Long productSkuId = entry.getKey();
            Integer count = entry.getValue();
            // ????????????????????? https://www.cnblogs.com/lichihua/p/10803305.html
            int updateCount = skuStockDao.lockStock(productSkuId, count);
            log.debug("productSkuId = {}, count = {}, updateCount = {}", productSkuId, count, updateCount);
            if (updateCount == 0) {
                String productName = cartItems.stream().filter(c -> productSkuId.equals(c.getProductSkuId())).findAny().map(CartItem::getProductName).orElse("");
                throw new ServiceException(StrUtil.format("{} ???????????????", productName));
            }
        }

        // ???????????????????????????
        Order order = generatePrice(currentMember, userAddress, cartItems);
        order.setCreateTime(new Date());
        // 0->????????????1->????????????2->??????
        order.setPayType(1);
        // 0->PC?????????1->app??????
        order.setSourceType(1);
        // 0->????????????1->????????????2->????????????3->????????????4->????????????5->????????????
        order.setStatus(0);
        // 0->???????????????1->????????????
        order.setOrderType(0);

        // ??????
        order.setIntegration(0);
        // ?????????
        order.setGrowth(0);

        // ID???id?????????
        Long orderId = idService.generateOrderId();
        order.setId(orderId);
        // TODO orderSn
        String orderSn = orderId + "";
        order.setOrderSn(orderSn);

        // ????????????
        order.setAutoConfirmDay(15);

        // TODO ????????????????????????

        // ???????????????????????????????????????
        cartItemDao.deleteBatchIds(saveOrderParam.getCartItemIds());

        // TODO ??????????????????????????????????????????

        orderDao.insert(order);


        // ??????????????????
        // ?????????????????????
        List<OrderItem> orderItems = cartItems.stream()
                .map(c -> {
                    OrderItem orderItem = new OrderItem(c);
                    orderItem.setProductAttr(c.getProductAttr());
                    orderItem.setProductPic(c.getProductPic());
                    orderItem.setOrderId(order.getId());
                    orderItem.setOrderSn(order.getOrderSn());
                    return orderItem;
                })
                .collect(Collectors.toList());
        // ????????????????????????????????????????????????
        Set<Long> productIds = cartItems.stream().map(CartItem::getProductId).collect(Collectors.toSet());
        Map<Long, Product> productId2Product = productDao.selectBatchIds(productIds)
                .stream()
                .collect(Collectors.toMap(p -> p.getId(), p -> p));
        for (OrderItem orderItem : orderItems) {
            Long productId = orderItem.getProductId();
            Product product = productId2Product.get(productId);

            // TODO ????????????
//            BigDecimal promotionPrice = product.getPromotionPrice();
//            orderItem.setPromotionAmount(promotionPrice.multiply(new BigDecimal(orderItem.getProductQuantity())));

            orderItem.setProductPrice(product.getPrice().multiply(new BigDecimal(orderItem.getProductQuantity())));

            orderItemDao.insert(orderItem);
        }


        log.debug("userId = {}, order = {}", currentMember.getId(), JSON.toJSONString(order));
        log.info("userId = {}, ???????????? = {}", currentMember.getId(), orderSn);

        orderCancelPool.execute(() -> {
            Integer overtime = Optional.ofNullable(orderSettingDao.selectOne(null))
                    .map(OrderSetting::getNormalOrderOvertime)
                    .orElse(1);
            log.info("orderId = {} ???????????????{}min???????????????????????????", orderId, overtime);
            orderDelayQueue.offer(orderId + "", overtime, TimeUnit.MINUTES);
        });

        return orderSn;
    }

    private Order generatePrice(Member currentMember, UserAddress userAddress, List<CartItem> cartItems) {
        Order order = new Order();

        // ???????????????
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
        // ???????????????????????????????????????????????????????????????
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
        // ???????????? = ???????????? + ?????? - ???????????? - ???????????? - ?????????
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
        // ??????????????????
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
        return cancelOrder(orderId, false);
    }

    public int cancelOrder(String orderId, boolean auto) {
        Member currentMember = userService.getCurrentMember();
        log.info("currentMember = {}, ????????????, auto = {}, orderId = {}", currentMember, auto, orderId);
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<Order>()
                .eq(Order::getId, orderId);
        if (!auto) {
            wrapper.eq(Order::getMemberId, currentMember.getId());
        }

        // ???????????????????????????
        Order order = orderDao.selectById(orderId);

        RLock orderLock = redisson.getLock("order:id:" + orderId);
        orderLock.lock();
        log.info("???????????? = {}", JSON.toJSONString(order));
        try {
            Integer status = order.getStatus();
            if (auto && status != OrderStatusEnum.TO_PAY.getCode()) {
                log.warn("orderId = {}???????????????????????????????????????", orderId);
                return 0;
            }

            if (OrderStatusEnum.TO_PAY.getCode() == status) {
                LambdaQueryWrapper<OrderItem> cartItemWrapper = new LambdaQueryWrapper<OrderItem>()
                        .eq(OrderItem::getOrderId, orderId);
                List<OrderItem> orderItems = orderItemDao.selectList(cartItemWrapper);
                Map<Long, Integer> productSkuId2Count = orderItems.stream()
                        .collect(Collectors.groupingBy(p -> p.getProductSkuId(), Collectors.mapping(p -> p.getProductQuantity(), Collectors.summingInt(i -> i))));
                for (Map.Entry<Long, Integer> entry : productSkuId2Count.entrySet()) {
                    Long productSkuId = entry.getKey();
                    Integer count = entry.getValue();
                    log.debug("???????????????????????? productSkuId = {}, count = {}", productSkuId, count);
                    skuStockDao.lockStock(productSkuId, -count);
                }
            } else if (OrderStatusEnum.TO_POST_PRODUCT.getCode() == status) {
                LambdaQueryWrapper<OrderItem> cartItemWrapper = new LambdaQueryWrapper<OrderItem>()
                        .eq(OrderItem::getOrderId, orderId);
                List<OrderItem> orderItems = orderItemDao.selectList(cartItemWrapper);
                Map<Long, Integer> productSkuId2Count = orderItems.stream()
                        .collect(Collectors.groupingBy(p -> p.getProductSkuId(), Collectors.mapping(p -> p.getProductQuantity(), Collectors.summingInt(i -> i))));
                for (Map.Entry<Long, Integer> entry : productSkuId2Count.entrySet()) {
                    Long productSkuId = entry.getKey();
                    Integer count = entry.getValue();
                    log.debug("??????????????? productSkuId = {}, count = {}", productSkuId, count);
                    skuStockDao.incrTrueStock(productSkuId, count);
                }
            } else {
                log.warn("???????????????order id = {}, status = {}", order.getId(), status);
            }
            order.setStatus(OrderStatusEnum.INVALID.getCode());
            int count = orderDao.update(order, wrapper);
            return count;
        } finally {
            orderLock.unlock();
        }

    }

    @Override
    public int confirmReceive(String orderId) {
        Member currentMember = userService.getCurrentMember();
        log.info("userId = {}, ????????????, orderId = {}", currentMember.getId(), orderId);
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

        // ??????????????????
        LambdaQueryWrapper<OrderItem> orderItemWrapper = new LambdaQueryWrapper<OrderItem>()
                .eq(OrderItem::getOrderId, orderId);
        List<OrderItem> orderItems = orderItemDao.selectList(orderItemWrapper);
        orderItems.forEach(orderItem -> {
                    skuStockDao.decrTrueStock(orderItem.getProductSkuId(), orderItem.getProductQuantity());
                }
        );
        log.info("userId = {}, ???????????????", currentMember.getId());
        return 1;
    }


}
