package com.EcommerceSite.Ecommerce.website.service.impl;

import com.EcommerceSite.Ecommerce.website.domain.AccountStatus;
import com.EcommerceSite.Ecommerce.website.entity.SellerEntity;
import com.EcommerceSite.Ecommerce.website.entity.SellerReport;
import com.EcommerceSite.Ecommerce.website.exceptions.SellerException;
import com.EcommerceSite.Ecommerce.website.repository.SellerReportRepository;
import com.EcommerceSite.Ecommerce.website.service.SellerReportService;
import com.EcommerceSite.Ecommerce.website.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SellerReportServiceImpl implements SellerReportService {
    @Autowired
    private SellerReportRepository sellerReportRepository;
    @Autowired
    private SellerReportRepository reportRepository;

    @Override
    public SellerReport getSellerReport(SellerEntity seller) {

        SellerReport sr = sellerReportRepository.findBySellerId(seller.getId());

        if (sr == null) {
            SellerReport newReport = new SellerReport();
            newReport.setSeller(seller);
            return sellerReportRepository.save(newReport);
        }

        return sr;
    }

    @Override
    public SellerReport updateSellerReport(SellerReport sellerReport) {
        return sellerReportRepository.save(sellerReport);
    }
}
