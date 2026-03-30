package com.EcommerceSite.Ecommerce.website.service.impl;

import com.EcommerceSite.Ecommerce.website.entity.ProductEntity;
import com.EcommerceSite.Ecommerce.website.entity.UserEntity;
import com.EcommerceSite.Ecommerce.website.entity.Wishlist;
import com.EcommerceSite.Ecommerce.website.repository.WishListRepository;
import com.EcommerceSite.Ecommerce.website.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishlistServiceImpl implements WishlistService {
    @Autowired
    private WishListRepository wishListRepository;
    @Override
    public Wishlist createWishlist(UserEntity user) {
        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        return wishListRepository.save(wishlist);
    }

    @Override
    public Wishlist getWishlistByUserId(UserEntity user) {
        Wishlist wishlist = wishListRepository.findByUserId(user.getId());

        if(wishlist == null){
            wishlist=createWishlist(user);
        }
        return wishlist;
    }

    @Override
    public Wishlist addProductionToWishlist(UserEntity user, ProductEntity product) {
        Wishlist wishlist = getWishlistByUserId(user);

        if(wishlist.getProducts().contains(product)){
            wishlist.getProducts().remove(product);
        }
        else wishlist.getProducts().add(product);

        return wishListRepository.save(wishlist);
    }
}
