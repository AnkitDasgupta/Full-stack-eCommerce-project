package com.EcommerceSite.Ecommerce.website.service;

import com.EcommerceSite.Ecommerce.website.domain.AccountStatus;
import com.EcommerceSite.Ecommerce.website.entity.SellerEntity;
import com.EcommerceSite.Ecommerce.website.exceptions.SellerException;

import java.util.List;

public interface SellerService {

    SellerEntity getSellerProfile(String jwt) throws Exception;
    SellerEntity createSeller(SellerEntity seller) throws Exception;
    SellerEntity getSellerById(Long id) throws SellerException;
    SellerEntity getSellerByEmail(String email) throws Exception;
    List<SellerEntity> getAllSeller(AccountStatus status);
    SellerEntity updateSeller(Long id , SellerEntity seller) throws Exception;
    void deleteSeller(Long id) throws Exception;
    SellerEntity verifyEmail(String email , String otp) throws Exception;
    SellerEntity updateSellerAccountStatus(Long sellerId,AccountStatus status) throws Exception;


    List<SellerEntity> getAllSellers(AccountStatus status);
}
