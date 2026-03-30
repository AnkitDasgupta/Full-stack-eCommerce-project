package com.EcommerceSite.Ecommerce.website.cntroller;

import com.EcommerceSite.Ecommerce.website.entity.SellerEntity;
import com.EcommerceSite.Ecommerce.website.entity.Transaction;
import com.EcommerceSite.Ecommerce.website.exceptions.SellerException;
import com.EcommerceSite.Ecommerce.website.service.SellerService;
import com.EcommerceSite.Ecommerce.website.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    @Autowired
    private SellerService sellerService;

    @GetMapping("/seller")
    public ResponseEntity<List<Transaction>> getTransactionBySeller(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        SellerEntity seller = sellerService.getSellerProfile(jwt);

        List<Transaction> transactions = transactionService.getTransactionsBySellerId(seller);

        return ResponseEntity.ok(transactions);
    }
    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransaction(){
        List<Transaction> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }
}
