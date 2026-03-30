package com.EcommerceSite.Ecommerce.website.service.impl;

import com.EcommerceSite.Ecommerce.website.entity.CartItemEntity;
import com.EcommerceSite.Ecommerce.website.entity.UserEntity;
import com.EcommerceSite.Ecommerce.website.repository.CartItemRepository;
import com.EcommerceSite.Ecommerce.website.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;
    @Override
    public CartItemEntity updateCartItem(Long userId, Long id, CartItemEntity cartItem) throws Exception {

        CartItemEntity item = findCartItemById(id);

        UserEntity cartItemUser = item.getCart().getUser();

        if (cartItemUser.getId().equals(userId)) {
            item.setQuantity(cartItem.getQuantity());
            item.setMrpPrice(item.getQuantity() * item.getProduct().getMrpPrice());
            item.setSellingPrice(item.getQuantity() * item.getProduct().getSellingPrice());

            return cartItemRepository.save(item);
        }


        throw new Exception("You can't update this cartItem");
    }

    @Override
    public void removeCartItem(Long userId, Long cartItemId) throws Exception {

        CartItemEntity item = findCartItemById(cartItemId);

        UserEntity cartItemUser = item.getCart().getUser();

        if (cartItemUser.getId().equals(userId)) {
            cartItemRepository.delete(item);
        } else {
            throw new Exception("You can't delete this item");
        }
    }

    @Override
    public CartItemEntity findCartItemById(Long id) throws Exception {

        return cartItemRepository.findById(id)
                .orElseThrow(() ->
                        new Exception("Cart item not found with id: " + id)
                );
    }

}

