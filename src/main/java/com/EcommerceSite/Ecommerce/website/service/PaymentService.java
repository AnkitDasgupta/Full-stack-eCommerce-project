package com.EcommerceSite.Ecommerce.website.service;

import com.EcommerceSite.Ecommerce.website.entity.OrderEntity;
import com.EcommerceSite.Ecommerce.website.entity.PaymentOrder;
import com.EcommerceSite.Ecommerce.website.entity.UserEntity;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;

import java.util.Set;

public interface PaymentService {

    PaymentOrder createOrder(UserEntity user, Set<OrderEntity> orders);
    PaymentOrder getPaymentOrderById(Long orderId) throws Exception;
    PaymentOrder getPaymentOrderByPaymentId(String orderId) throws Exception;
    Boolean proceedPaymentOrder(PaymentOrder paymentOrder,
                                String paymentId,
                                String paymentLinkId) throws RazorpayException;
    PaymentLink createRazorpayPaymentLink(UserEntity user, Long amount, Long orderId) throws RazorpayException;
    String createStripePaymentLink(UserEntity user,
                                        Long amount,Long orderId) throws StripeException;

}
