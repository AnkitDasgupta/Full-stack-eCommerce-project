package com.EcommerceSite.Ecommerce.website.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class CartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "User_id")
    private UserEntity user;

    @OneToMany(mappedBy = "cart" , cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<CartItemEntity> cartItems = new HashSet<>();

    private double totalSellingPrice;

    private int totalItem;

    private int totalMrpPrice;

    private int discount;

    private String couponCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Set<CartItemEntity> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Set<CartItemEntity> cartItems) {
        this.cartItems = cartItems;
    }

    public double getTotalSellingPrice() {
        return totalSellingPrice;
    }

    public void setTotalSellingPrice(double totalSellingPrice) {
        this.totalSellingPrice = totalSellingPrice;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public int getTotalMrpPrice() {
        return totalMrpPrice;
    }

    public void setTotalMrpPrice(int totalMrpPrice) {
        this.totalMrpPrice = totalMrpPrice;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public CartEntity(Long id, UserEntity user, Set<CartItemEntity> cartItems, double totalSellingPrice, int totalItem, int totalMrpPrice, int discount, String couponCode) {
        this.id = id;
        this.user = user;
        this.cartItems = cartItems;
        this.totalSellingPrice = totalSellingPrice;
        this.totalItem = totalItem;
        this.totalMrpPrice = totalMrpPrice;
        this.discount = discount;
        this.couponCode = couponCode;
    }

    public CartEntity(){


    }
}
