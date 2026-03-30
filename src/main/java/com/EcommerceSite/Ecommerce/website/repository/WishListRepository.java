package com.EcommerceSite.Ecommerce.website.repository;

import com.EcommerceSite.Ecommerce.website.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishListRepository extends JpaRepository<Wishlist,Long> {

    Wishlist findByUserId(Long userId);
}
