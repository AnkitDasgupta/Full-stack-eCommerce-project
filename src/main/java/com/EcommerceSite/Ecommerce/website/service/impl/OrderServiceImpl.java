package com.EcommerceSite.Ecommerce.website.service.impl;

import com.EcommerceSite.Ecommerce.website.domain.OrderStatus;
import com.EcommerceSite.Ecommerce.website.domain.PaymentStatus;
import com.EcommerceSite.Ecommerce.website.entity.*;
import com.EcommerceSite.Ecommerce.website.repository.AddressRepository;
import com.EcommerceSite.Ecommerce.website.repository.OrderItemRepository;
import com.EcommerceSite.Ecommerce.website.repository.OrderRepository;
import com.EcommerceSite.Ecommerce.website.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Override
    public Set<OrderEntity> createOrder(UserEntity user, AddressEntity shippingAddress, CartEntity cart) {

        if (!user.getAddress().contains(shippingAddress)) {
            user.getAddress().add(shippingAddress);
        }
        AddressEntity address = addressRepository.save(shippingAddress);

        Map<Long, List<CartItemEntity>> itemsBySeller = cart.getCartItems().stream()
                .collect(Collectors.groupingBy(item -> item.getProduct().getSeller().getId()));

        Set<OrderEntity> orders = new HashSet<>();

        for (Map.Entry<Long, List<CartItemEntity>> entry : itemsBySeller.entrySet()) {

            Long sellerId = entry.getKey();
            List<CartItemEntity> items = entry.getValue();

            int totalOrderPrice = items.stream()
                    .mapToInt(CartItemEntity::getSellingPrice)
                    .sum();

            int totalItem = items.stream()
                    .mapToInt(CartItemEntity::getQuantity)
                    .sum();

            OrderEntity createdOrder = new OrderEntity();
            createdOrder.setUser(user);
            createdOrder.setSellerId(sellerId);
            createdOrder.setTotalMrpPrice(totalOrderPrice);
            createdOrder.setTotalSellingPrice(totalOrderPrice);
            createdOrder.setTotalItem(totalItem);
            createdOrder.setShippingAddress(address);
            createdOrder.setOrderStatus(OrderStatus.PENDING);
            createdOrder.getPaymentDetails().setStatus(PaymentStatus.PENDING);

            OrderEntity savedOrder = orderRepository.save(createdOrder);
            orders.add(savedOrder);

            List<OrderItemEntity> orderItems = new ArrayList<>();

            for (CartItemEntity item : items) {
                OrderItemEntity orderItem = new OrderItemEntity();
                orderItem.setOrder(savedOrder);
                orderItem.setMrpPrice(item.getMrpPrice());
                orderItem.setProductEntity(item.getProduct());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setSize(item.getSize());
                orderItem.setUserId(item.getUserId());
                orderItem.setSellingPrice(item.getSellingPrice());

                savedOrder.getOrderItem().add(orderItem);

                OrderItemEntity savedOrderItem =orderItemRepository.save(orderItem);
                orderItems.add(savedOrderItem);
            }


        }
        return orders;
    }
    @Override
    public OrderEntity findOrderById(Long id) throws Exception {
        return orderRepository.findById(id).orElseThrow(()->
        new Exception("Order not found..."));
    }

    @Override
    public List<OrderEntity> usersOrderHistory(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    public List<OrderEntity> sellersOrder(Long sellerId) {
        return orderRepository.findBySellerId(sellerId);
    }

    @Override
    public OrderEntity updateOrderStatus(Long orderId, OrderStatus orderStatus) throws Exception {
         OrderEntity order = findOrderById(orderId);
         order.setOrderStatus(orderStatus);
         return orderRepository.save(order);
    }

    @Override
    public OrderEntity cancelOrder(Long orderId, UserEntity user) throws Exception {

        OrderEntity order = findOrderById(orderId);

        if(!user.getId().equals(order.getUser().getId())){

            throw new Exception("You don't have access to this order");
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);


    }

    @Override
    public OrderItemEntity getOrderItemById(Long id) throws Exception {

        return orderItemRepository.findById(id).orElseThrow(()->
        new Exception("Order item not exist..."));
    }
}
