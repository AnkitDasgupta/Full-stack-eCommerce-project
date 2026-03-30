package com.EcommerceSite.Ecommerce.website.service;

import com.EcommerceSite.Ecommerce.website.entity.CartItemEntity;

public interface CartItemService {

    CartItemEntity updateCartItem(Long userId,Long id,CartItemEntity cartItem) throws Exception;
    void removeCartItem(Long userId,Long cartItemId) throws Exception;
    CartItemEntity findCartItemById(Long id) throws Exception;
}
