package com.EcommerceSite.Ecommerce.website.repository;

import com.EcommerceSite.Ecommerce.website.domain.AccountStatus;
import com.EcommerceSite.Ecommerce.website.entity.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerRepository extends JpaRepository<SellerEntity,Long> {

    SellerEntity findByEmail(String email);
    List<SellerEntity>findByAccountStatus(AccountStatus status);
}
