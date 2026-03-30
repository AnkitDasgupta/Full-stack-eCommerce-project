package com.EcommerceSite.Ecommerce.website.repository;

import com.EcommerceSite.Ecommerce.website.entity.CouponEntity;
import com.stripe.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<CouponEntity,Long> {

    CouponEntity findByCode(String code);

}
