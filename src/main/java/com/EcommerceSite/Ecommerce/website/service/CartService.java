package com.EcommerceSite.Ecommerce.website.service;

import com.EcommerceSite.Ecommerce.website.entity.CartEntity;
import com.EcommerceSite.Ecommerce.website.entity.CartItemEntity;
import com.EcommerceSite.Ecommerce.website.entity.ProductEntity;
import com.EcommerceSite.Ecommerce.website.entity.UserEntity;

public interface CartService {

    public CartItemEntity addCartItem(UserEntity user,
                                      ProductEntity product,
                                      String size,
                                      int quantity);

    public CartEntity findUserCart(UserEntity user);
}
