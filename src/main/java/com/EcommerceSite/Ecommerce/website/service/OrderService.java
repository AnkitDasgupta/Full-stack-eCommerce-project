package com.EcommerceSite.Ecommerce.website.service;

import com.EcommerceSite.Ecommerce.website.domain.OrderStatus;
import com.EcommerceSite.Ecommerce.website.entity.*;

import java.util.List;
import java.util.Set;

public interface OrderService {

    Set<OrderEntity> createOrder(UserEntity user, AddressEntity shippingAddress, CartEntity cart);

    OrderEntity findOrderById(Long id) throws Exception;

    List<OrderEntity> usersOrderHistory(Long userId);

    List<OrderEntity> sellersOrder(Long sellerId);

    OrderEntity updateOrderStatus(Long orderId, OrderStatus orderStatus) throws Exception;
    OrderEntity cancelOrder(Long orderId,UserEntity user) throws Exception;
    OrderItemEntity getOrderItemById(Long id) throws Exception;

}
