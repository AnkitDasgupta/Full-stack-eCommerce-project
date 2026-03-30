package com.EcommerceSite.Ecommerce.website.cntroller;

import com.EcommerceSite.Ecommerce.website.entity.CartEntity;
import com.EcommerceSite.Ecommerce.website.entity.CouponEntity;
import com.EcommerceSite.Ecommerce.website.entity.UserEntity;
import com.EcommerceSite.Ecommerce.website.service.CartService;
import com.EcommerceSite.Ecommerce.website.service.CouponService;
import com.EcommerceSite.Ecommerce.website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
public class AdminCouponController {
@Autowired
private CouponService couponService;
@Autowired
private UserService userService;
@Autowired
private CartService cartService;


    @PostMapping("/apply")
    public ResponseEntity<CartEntity> applyCoupon(
            @RequestParam String apply,
            @RequestParam String code,
            @RequestParam double orderValue,
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        UserEntity user = userService.findUserByJwtToken(jwt);
        CartEntity cart;

        if (apply.equals("true")) {
            cart = couponService.applyCoupon(code, orderValue, user);
        } else {
            cart = couponService.removeCoupon(code, user);
        }

        return ResponseEntity.ok(cart);
    }

    @PostMapping("/admin/create")
    public ResponseEntity<CouponEntity> createCoupon(@RequestBody CouponEntity coupon) {
        CouponEntity createdCoupon = couponService.createCoupon(coupon);
        return ResponseEntity.ok(createdCoupon);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteCoupon(@PathVariable Long id) throws Exception {
        couponService.deleteCoupon(id);
        return ResponseEntity.ok("Coupon deleted successfully");
    }

    @GetMapping("/admin/all")
    public ResponseEntity<List<CouponEntity>> getAllCoupons() {
        List<CouponEntity> coupons = couponService.findAllCoupons();
        return ResponseEntity.ok(coupons);
    }
}
