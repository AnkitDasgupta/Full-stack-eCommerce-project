package com.EcommerceSite.Ecommerce.website.service;

import com.EcommerceSite.Ecommerce.website.entity.OrderEntity;
import com.EcommerceSite.Ecommerce.website.entity.SellerEntity;
import com.EcommerceSite.Ecommerce.website.entity.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction createTransaction(OrderEntity order);

    List<Transaction> getTransactionsBySellerId(SellerEntity seller);

    List<Transaction> getAllTransactions();

}
