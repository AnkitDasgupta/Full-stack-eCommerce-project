package com.EcommerceSite.Ecommerce.website.service;

import com.EcommerceSite.Ecommerce.website.entity.Home;
import com.EcommerceSite.Ecommerce.website.entity.HomeCategory;

import java.util.List;

public interface  HomeService {

     Home createHomePageDate(List<HomeCategory> allCategory);
}
