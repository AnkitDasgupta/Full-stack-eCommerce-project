package com.EcommerceSite.Ecommerce.website.service.impl;

import com.EcommerceSite.Ecommerce.website.configuration.JwtProvider;
import com.EcommerceSite.Ecommerce.website.domain.AccountStatus;
import com.EcommerceSite.Ecommerce.website.domain.USER_ROLE;
import com.EcommerceSite.Ecommerce.website.entity.AddressEntity;
import com.EcommerceSite.Ecommerce.website.entity.SellerEntity;
import com.EcommerceSite.Ecommerce.website.exceptions.SellerException;
import com.EcommerceSite.Ecommerce.website.repository.AddressRepository;
import com.EcommerceSite.Ecommerce.website.repository.SellerRepository;
import com.EcommerceSite.Ecommerce.website.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerRepository sellerRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AddressRepository addressRepository;
    @Override
    public SellerEntity getSellerProfile(String jwt) throws Exception {

        String email = jwtProvider.getEmailFromJwtToken(jwt);

        return this.getSellerByEmail(email);
    }

    @Override
    public SellerEntity createSeller(SellerEntity seller) throws Exception {

        SellerEntity sellerExist =sellerRepository.findByEmail(seller.getEmail());
        if(sellerExist!= null){

            throw new Exception("seller already exist , used different email");

        }

        AddressEntity savedAddress = addressRepository.save(seller.getPickUpAddress());

        SellerEntity newSeller = new SellerEntity();
        newSeller.setEmail(seller.getEmail());
        newSeller.setPassword(passwordEncoder.encode(seller.getPassword()));
        newSeller.setSellerName(seller.getSellerName());
        newSeller.setRole(USER_ROLE.ROLE_SELLER);
        newSeller.setBankDetails(seller.getBankDetails());
        newSeller.setGstIn(seller.getGstIn());
        newSeller.setMobile(seller.getMobile());
        newSeller.setPickUpAddress(savedAddress);
        newSeller.setBusinessDetails(seller.getBusinessDetails());

        return sellerRepository.save(newSeller);
    }

    @Override
    public SellerEntity getSellerById(Long id) throws SellerException {

        return sellerRepository.findById(id)
                .orElseThrow(()->new SellerException("seller not found with id " + id));

    }

    @Override
    public SellerEntity getSellerByEmail(String email) throws Exception {

        SellerEntity seller = sellerRepository.findByEmail(email);
        if(seller == null){

            throw new Exception("seller not found ...");
        }
        return seller;
    }

    @Override
    public List<SellerEntity> getAllSeller(AccountStatus status) {
        return sellerRepository.findByAccountStatus(status);
    }

    @Override
    public SellerEntity updateSeller(Long id, SellerEntity seller) throws Exception {

        SellerEntity existingSeller = this.getSellerById(id);


        if(seller.getSellerName()!= null){

            existingSeller.setSellerName(seller.getSellerName());
        }
        if(seller.getMobile()!= null){
            existingSeller.setMobile(seller.getMobile());
        }
        if(seller.getEmail()!= null){

            existingSeller.setEmail(seller.getEmail());
        }
        if(seller.getBusinessDetails()!= null
                && seller.getBusinessDetails().getBusinessName() != null
        ){
            existingSeller.getBusinessDetails().setBusinessName(seller.getBusinessDetails().getBusinessName());

        }
         if(seller.getBankDetails()!= null
                 && seller.getBankDetails().getAccountHolderName() != null
                 && seller.getBankDetails().getIfscCode() != null
                 && seller.getBankDetails().getAccountNumber() != null
         ){

             existingSeller.getBankDetails().setAccountHolderName(
                     seller.getBankDetails().getAccountHolderName()
             );
             existingSeller.getBankDetails().setAccountNumber(
                     seller.getBankDetails().getIfscCode()
             );
             existingSeller.getBankDetails().setIfscCode(
                     seller.getBankDetails().getIfscCode()
             );
         }

         if(seller.getPickUpAddress() != null
              && seller.getPickUpAddress().getAddress() != null
              && seller.getPickUpAddress().getMobile() != null
              && seller.getPickUpAddress().getCity() != null
              && seller.getPickUpAddress().getState() != null
         ){
             existingSeller.getPickUpAddress()
                     .setAddress(seller.getPickUpAddress().getAddress());
             existingSeller.getPickUpAddress().setCity(seller.getPickUpAddress().getCity());
             existingSeller.getPickUpAddress().setState(seller.getPickUpAddress().getState());
             existingSeller.getPickUpAddress().setMobile(seller.getPickUpAddress().getMobile());
             existingSeller.getPickUpAddress().setPinCode(seller.getPickUpAddress().getPinCode());

         }
         if(seller.getGstIn() != null){
             existingSeller.setGstIn(seller.getGstIn());
         }



        return sellerRepository.save(existingSeller);
    }

    @Override
    public void deleteSeller(Long id) throws Exception {

        SellerEntity seller = getSellerById(id);
        sellerRepository.delete(seller);
    }

    @Override
    public SellerEntity verifyEmail(String email, String otp) throws Exception {

        SellerEntity seller = getSellerByEmail(email);
        seller.setEmailVerified(true);
        return sellerRepository.save(seller);
    }

    @Override
    public SellerEntity updateSellerAccountStatus(Long sellerId, AccountStatus status) throws Exception {
        SellerEntity seller = getSellerById(sellerId);
        seller.setAccountStatus(status);
        return sellerRepository.save(seller);
    }

    @Override
    public List<SellerEntity> getAllSellers(AccountStatus status) {
        return null;
    }
}
