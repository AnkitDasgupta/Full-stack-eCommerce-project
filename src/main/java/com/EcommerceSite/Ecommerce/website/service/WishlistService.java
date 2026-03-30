package com.EcommerceSite.Ecommerce.website.service;

import com.EcommerceSite.Ecommerce.website.entity.ProductEntity;
import com.EcommerceSite.Ecommerce.website.entity.UserEntity;
import com.EcommerceSite.Ecommerce.website.entity.Wishlist;

public interface WishlistService {

    Wishlist createWishlist(UserEntity user);
    Wishlist getWishlistByUserId(UserEntity user);
    Wishlist addProductionToWishlist(UserEntity user, ProductEntity product);
}
