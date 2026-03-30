package com.EcommerceSite.Ecommerce.website.cntroller;

import com.EcommerceSite.Ecommerce.website.domain.OrderStatus;
import com.EcommerceSite.Ecommerce.website.entity.OrderEntity;
import com.EcommerceSite.Ecommerce.website.entity.SellerEntity;
import com.EcommerceSite.Ecommerce.website.service.OrderService;
import com.EcommerceSite.Ecommerce.website.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seller/orders")
public class SellerOrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private SellerService sellerService;

    @GetMapping()
    public ResponseEntity<List<OrderEntity>> getAllOrdersHandler(
            @RequestHeader("Authorization") String jwt
    ) throws  Exception {

        SellerEntity seller = sellerService.getSellerProfile(jwt);
        List<OrderEntity> orders = orderService.sellersOrder(seller.getId());

        return new ResponseEntity<>(orders, HttpStatus.ACCEPTED);
    }

    @PatchMapping("/{orderId}/status/{orderStatus}")
    public ResponseEntity<OrderEntity> updateOrderHandler(
            @RequestHeader("Authorization") String jwt,
            @PathVariable Long orderId,
            @PathVariable OrderStatus orderStatus
    ) throws  Exception {

        OrderEntity order = orderService.updateOrderStatus(orderId,orderStatus);

        return new ResponseEntity<>(order,HttpStatus.ACCEPTED);
    }

}
