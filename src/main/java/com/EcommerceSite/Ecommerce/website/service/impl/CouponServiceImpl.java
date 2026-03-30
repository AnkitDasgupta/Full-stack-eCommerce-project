package com.EcommerceSite.Ecommerce.website.service.impl;

import com.EcommerceSite.Ecommerce.website.entity.CartEntity;
import com.EcommerceSite.Ecommerce.website.entity.CouponEntity;
import com.EcommerceSite.Ecommerce.website.entity.UserEntity;
import com.EcommerceSite.Ecommerce.website.repository.CartRepository;
import com.EcommerceSite.Ecommerce.website.repository.CouponRepository;
import com.EcommerceSite.Ecommerce.website.repository.UserRepository;
import com.EcommerceSite.Ecommerce.website.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public CartEntity applyCoupon(String code, double orderValue, UserEntity user) throws Exception {
        CouponEntity coupon = couponRepository.findByCode(code);

        CartEntity cart = cartRepository.findByUserId(user.getId());

        if(coupon==null){
            throw new Exception("coupon not valid");
        }
        if(user.getUsedCoupons().contains(coupon)){
            throw new Exception("coupon already used");
        }
        if(orderValue<coupon.getMinimumOrderValue()){
            throw new Exception("valid for minimum order value " + coupon.getMinimumOrderValue());
        }
        if(coupon.isActive() &&
                LocalDate.now().isAfter(coupon.getValidityStartDate())
        && LocalDate.now().isBefore(coupon.getValidityEndDate())){

            user.getUsedCoupons().add(coupon);
            userRepository.save(user);

            double discountedPrice = (cart.getTotalSellingPrice()*coupon.getDiscountParcentage())/100;

            cart.setTotalSellingPrice(cart.getTotalSellingPrice()-discountedPrice);
            cart.setCouponCode(code);
            cartRepository.save(cart);

            return cart;
        }
        throw new Exception("coupon not valid");
    }

    @Override
    public CartEntity removeCoupon(String code, UserEntity user) throws Exception {

        CouponEntity coupon = couponRepository.findByCode(code);

        if (coupon == null) {
            throw new Exception("coupon not found...");
        }

        CartEntity cart = cartRepository.findByUserId(user.getId());

        double discountedPrice = (cart.getTotalSellingPrice() * coupon.getDiscountParcentage())/100  ;

        cart.setTotalSellingPrice(cart.getTotalSellingPrice() - discountedPrice);
        cart.setCouponCode(null);

        return cartRepository.save(cart);
    }

    @Override
    public CouponEntity findCouponById(Long id) throws Exception {
        return couponRepository.findById(id).orElseThrow(() ->
                new Exception("coupon not found"));
    }

    @Override
    @PreAuthorize("hasRole ('ADMIN')")
    public CouponEntity createCoupon(CouponEntity coupon) {
        return couponRepository.save(coupon);
    }

    @Override
    public List<CouponEntity> findAllCoupons() {
        return null;
    }

    @Override
    @PreAuthorize("hasRole ('ADMIN')")
    public void deleteCoupon(Long id) throws Exception {

        findCouponById(id);
        couponRepository.deleteById(id);

    }
}
