package com.EcommerceSite.Ecommerce.website.service;

import com.EcommerceSite.Ecommerce.website.entity.DealEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DealService {

    List<DealEntity> getDeals();

    DealEntity createDeal(DealEntity deal);

    DealEntity updateDeal(DealEntity deal , Long id) throws Exception;
    void deleteDeal(Long id) throws Exception;
}
