package com.EcommerceSite.Ecommerce.website.cntroller;

import com.EcommerceSite.Ecommerce.website.entity.*;
import com.EcommerceSite.Ecommerce.website.repository.TransactionRepository;
import com.EcommerceSite.Ecommerce.website.responseDto.ApiResponse;
import com.EcommerceSite.Ecommerce.website.responseDto.PaymentLinkResponse;
import com.EcommerceSite.Ecommerce.website.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private UserService userService;
    @Autowired
    private SellerService sellerService;
    @Autowired
    private SellerReportService sellerReportService;
    @Autowired
    private TransactionService transactionService;


    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse> paymentSuccessHandler(
            @PathVariable String paymentId,
            @RequestParam String paymentLinkId,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        UserEntity user = userService.findUserByJwtToken(jwt);

        PaymentLinkResponse paymentResponse;

        PaymentOrder paymentOrder = paymentService
                .getPaymentOrderByPaymentId(paymentLinkId);

        boolean paymentSuccess = paymentService. proceedPaymentOrder(
                paymentOrder,
                paymentId,
                paymentLinkId
        );

        if (paymentSuccess) {
            for (OrderEntity order : paymentOrder.getOrders()) {

                transactionService.createTransaction(order);

                SellerEntity seller = sellerService.getSellerById(order.getSellerId());
                SellerReport report = sellerReportService.getSellerReport(seller);

                report.setTotalOrders(report.getTotalOrders() + 1);
                report.setTotalEarning(report.getTotalEarning() + order.getTotalSellingPrice());
                report.setTotalSales(report.getTotalSales() + order.getOrderItem().size());

                sellerReportService.updateSellerReport(report);
            }
        }

        ApiResponse res = new ApiResponse();
        res.setMessage("Payment successful");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

}
