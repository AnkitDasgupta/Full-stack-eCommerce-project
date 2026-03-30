package com.EcommerceSite.Ecommerce.website.repository;

import com.EcommerceSite.Ecommerce.website.entity.CartEntity;
import com.EcommerceSite.Ecommerce.website.entity.CartItemEntity;
import com.EcommerceSite.Ecommerce.website.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItemEntity,Long> {

    CartItemEntity findByCartAndProductAndSize(CartEntity cart , ProductEntity product,String size);

}
