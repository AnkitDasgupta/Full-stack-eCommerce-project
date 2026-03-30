package com.EcommerceSite.Ecommerce.website.service.impl;

import com.EcommerceSite.Ecommerce.website.entity.CartEntity;
import com.EcommerceSite.Ecommerce.website.entity.CartItemEntity;
import com.EcommerceSite.Ecommerce.website.entity.ProductEntity;
import com.EcommerceSite.Ecommerce.website.entity.UserEntity;
import com.EcommerceSite.Ecommerce.website.repository.CartItemRepository;
import com.EcommerceSite.Ecommerce.website.repository.CartRepository;
import com.EcommerceSite.Ecommerce.website.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public CartItemEntity addCartItem(UserEntity user, ProductEntity product, String size, int quantity) {

        CartEntity cart = findUserCart(user);

        CartItemEntity isPresent = cartItemRepository.findByCartAndProductAndSize(cart,product,size);

        if (isPresent == null) {

            CartItemEntity cartItem = new CartItemEntity();
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUserId(user.getId());
            cartItem.setSize(size);

            int totalPrice = quantity * product.getSellingPrice();
            cartItem.setSellingPrice(totalPrice);
            cartItem.setMrpPrice(quantity*product.getMrpPrice());

            cart.getCartItems().add(cartItem);
            cartItem.setCart(cart);

            return cartItemRepository.save(cartItem);
        }


        return isPresent;
    }

    @Override
    public CartEntity findUserCart(UserEntity user) {

        CartEntity cart = cartRepository.findByUserId(user.getId());

        int totalPrice = 0;
        int totalDiscountedPrice = 0;
        int totalItem = 0;

        for (CartItemEntity cartItem : cart.getCartItems()) {
            totalPrice += cartItem.getMrpPrice();
            totalDiscountedPrice += cartItem.getSellingPrice();
            totalItem += cartItem.getQuantity();
        }

        cart.setTotalMrpPrice(totalPrice);
        cart.setTotalSellingPrice(totalDiscountedPrice);
        cart.setTotalItem(totalItem);
        cart.setDiscount(calculateDiscountPercentage(totalPrice,totalDiscountedPrice));


        return cart;
    }

    private int calculateDiscountPercentage(int mrpPrice, int sellingPrice) {

        if (mrpPrice <= 0) {

            return 0;
        }
        double discount = mrpPrice - sellingPrice;
        double discountPercentage = (discount / mrpPrice) * 100;
        return (int) discountPercentage;


    }
}
