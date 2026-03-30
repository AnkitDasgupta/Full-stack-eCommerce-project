package com.EcommerceSite.Ecommerce.website.service.impl;

import com.EcommerceSite.Ecommerce.website.domain.HomeCategorySection;
import com.EcommerceSite.Ecommerce.website.entity.DealEntity;
import com.EcommerceSite.Ecommerce.website.entity.Home;
import com.EcommerceSite.Ecommerce.website.entity.HomeCategory;
import com.EcommerceSite.Ecommerce.website.repository.DealRepository;
import com.EcommerceSite.Ecommerce.website.service.HomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeServiceImpl implements HomeService {
    @Autowired
    private DealRepository dealRepository;
    @Override
    public Home createHomePageDate(List<HomeCategory> allCategories) {


        List<HomeCategory> gridCategories = allCategories.stream()
                .filter(category ->
                        category.getSection() == HomeCategorySection.GRID)
                .collect(Collectors.toList());

        List<HomeCategory> shopByCategories = allCategories.stream()
                .filter(category ->
                        category.getSection() == HomeCategorySection.SHOP_BY_CATEGORIES)
                .collect(Collectors.toList());

        List<HomeCategory> electricCategories = allCategories.stream()
                .filter(category ->
                        category.getSection() == HomeCategorySection.ELECTRIC_CATEGORIES)
                .collect(Collectors.toList());

        List<HomeCategory> dealCategories = allCategories.stream()
                .filter(category -> category.getSection() == HomeCategorySection.DEALS)
                .toList();

        List<DealEntity> createdDeals = new ArrayList<>();

        if (dealRepository.findAll().isEmpty()) {
            List<DealEntity> deals = allCategories.stream()
                    .filter(category -> category.getSection() == HomeCategorySection.DEALS)
                    .map(category -> new DealEntity(null, 10, category)) // Assuming id: null, discount: 10
                    .collect(Collectors.toList());

            createdDeals = dealRepository.saveAll(deals);
        } else {
            createdDeals = dealRepository.findAll();
        }

        Home home = new Home();
        home.setGrid(gridCategories);
        home.setShopByCategories(shopByCategories);
        home.setElectricCategories(electricCategories);
        home.setDeals(createdDeals);
        home.setDealCategories(dealCategories);

        return home;


    }
}
