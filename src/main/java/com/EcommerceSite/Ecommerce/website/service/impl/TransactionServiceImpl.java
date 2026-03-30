package com.EcommerceSite.Ecommerce.website.service.impl;

import com.EcommerceSite.Ecommerce.website.entity.OrderEntity;
import com.EcommerceSite.Ecommerce.website.entity.SellerEntity;
import com.EcommerceSite.Ecommerce.website.entity.Transaction;
import com.EcommerceSite.Ecommerce.website.repository.SellerRepository;
import com.EcommerceSite.Ecommerce.website.repository.TransactionRepository;
import com.EcommerceSite.Ecommerce.website.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private TransactionRepository transactionRepository;


    @Override
    public Transaction createTransaction(OrderEntity order) {

        SellerEntity seller = sellerRepository.findById(order.getSellerId()).get();

        Transaction transaction = new Transaction();
        transaction.setSeller(seller);
        transaction.setCustomer(order.getUser());
        transaction.setOrder(order);

        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getTransactionsBySellerId(SellerEntity seller) {
        return transactionRepository.findBySellerId(seller.getId());
    }

    @Override
    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }
}
