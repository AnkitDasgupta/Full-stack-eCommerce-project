package com.EcommerceSite.Ecommerce.website.service.impl;

import com.EcommerceSite.Ecommerce.website.entity.DealEntity;
import com.EcommerceSite.Ecommerce.website.entity.HomeCategory;
import com.EcommerceSite.Ecommerce.website.repository.DealRepository;
import com.EcommerceSite.Ecommerce.website.repository.HomeCategoryRepository;
import com.EcommerceSite.Ecommerce.website.service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DealServiceImpl implements DealService {

    @Autowired
    private DealRepository dealRepository;
    @Autowired
    private HomeCategoryRepository homeCategoryRepository;
    @Override
    public List<DealEntity> getDeals() {
        return dealRepository.findAll();
    }

    @Override
    public DealEntity createDeal(DealEntity deal) {
        HomeCategory category = homeCategoryRepository.findById(deal.getCategory().getId()).orElse(null);

        DealEntity newDeal = dealRepository.save(deal);
        newDeal.setCategory(category);
        newDeal.setDiscount(deal.getDiscount());

        return dealRepository.save(newDeal);
    }

    @Override
    public DealEntity updateDeal(DealEntity deal ,Long id) throws Exception {

        DealEntity existingDeal = dealRepository.findById(id).orElse(null);
        HomeCategory category = homeCategoryRepository.findById(deal.getCategory().getId()).orElse(null);

        if (existingDeal != null) {

            if (deal.getDiscount() != null) {
                existingDeal.setDiscount(deal.getDiscount());
            }

            if (category != null) {
                existingDeal.setCategory(category);
            }

            return dealRepository.save(existingDeal);
        }

        throw new Exception("Deal not found");
    }

    @Override
    public void deleteDeal(Long id) throws Exception {

        DealEntity deal = dealRepository.findById(id)
                .orElseThrow(() -> new Exception("deal not found"));

        dealRepository.delete(deal);

    }
}
