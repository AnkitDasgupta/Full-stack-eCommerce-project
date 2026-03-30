package com.EcommerceSite.Ecommerce.website.repository;

import com.EcommerceSite.Ecommerce.website.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity,Long>,
        JpaSpecificationExecutor<ProductEntity>
{

    List<ProductEntity> findBySellerId(Long id);
    @Query("SELECT p FROM ProductEntity p WHERE " +
            "(:query IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :query, '%'))) " +
            "OR (:query IS NULL OR LOWER(p.category.categoryId) LIKE LOWER(CONCAT('%', :query, '%')))")
    List<ProductEntity> searchProduct(@Param("query") String query);


}
