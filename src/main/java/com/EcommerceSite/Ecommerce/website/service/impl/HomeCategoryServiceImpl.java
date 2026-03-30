package com.EcommerceSite.Ecommerce.website.service.impl;

import com.EcommerceSite.Ecommerce.website.entity.HomeCategory;
import com.EcommerceSite.Ecommerce.website.repository.HomeCategoryRepository;
import com.EcommerceSite.Ecommerce.website.service.HomeCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeCategoryServiceImpl implements HomeCategoryService {

    @Autowired
    private HomeCategoryRepository homeCategoryRepository;
    @Override
    public HomeCategory createHomeCategory(HomeCategory homeCategory) {

        return homeCategoryRepository.save(homeCategory);
    }

    @Override
    public List<HomeCategory> createCategories(List<HomeCategory> homeCategories) {

        if(homeCategoryRepository.findAll().isEmpty()){
            return homeCategoryRepository.saveAll(homeCategories);
        }
        return homeCategoryRepository.findAll();

    }

    @Override
    public HomeCategory updateHomeCategory(HomeCategory category, Long id) throws Exception {

        HomeCategory existingCategory = homeCategoryRepository.findById(id)
                .orElseThrow(() -> new Exception("Category not found"));

        if (category.getImage() != null) {
            existingCategory.setImage(category.getImage());
        }

        if (category.getCategoryId() != null) {
            existingCategory.setCategoryId(category.getCategoryId());
        }

        return homeCategoryRepository.save(existingCategory);
    }

    @Override
    public List<HomeCategory> getAllHomeCategories() {
        return homeCategoryRepository.findAll();
    }
}
