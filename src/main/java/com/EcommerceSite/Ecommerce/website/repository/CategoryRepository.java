package com.EcommerceSite.Ecommerce.website.repository;

import com.EcommerceSite.Ecommerce.website.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {

    CategoryEntity findByCategoryId(String categoryId);
}
