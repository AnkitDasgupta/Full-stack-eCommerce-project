package com.EcommerceSite.Ecommerce.website.service;

import com.EcommerceSite.Ecommerce.website.entity.SellerEntity;
import com.EcommerceSite.Ecommerce.website.entity.SellerReport;

public interface SellerReportService {

    SellerReport getSellerReport(SellerEntity seller);
    SellerReport updateSellerReport(SellerReport sellerReport);
}
