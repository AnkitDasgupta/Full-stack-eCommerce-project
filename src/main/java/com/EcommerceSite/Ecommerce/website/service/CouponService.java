package com.EcommerceSite.Ecommerce.website.service;

import com.EcommerceSite.Ecommerce.website.entity.CartEntity;
import com.EcommerceSite.Ecommerce.website.entity.CouponEntity;
import com.EcommerceSite.Ecommerce.website.entity.UserEntity;

import java.util.List;

public interface CouponService {

    CartEntity applyCoupon(String code, double orderValue, UserEntity user) throws Exception;

    CartEntity removeCoupon(String code, UserEntity user) throws Exception;

    CouponEntity findCouponById(Long id) throws Exception;

    CouponEntity createCoupon(CouponEntity coupon);

    List<CouponEntity> findAllCoupons();

    void deleteCoupon(Long id) throws Exception;

}
