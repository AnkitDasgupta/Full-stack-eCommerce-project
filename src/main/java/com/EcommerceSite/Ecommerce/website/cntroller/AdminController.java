package com.EcommerceSite.Ecommerce.website.cntroller;

import com.EcommerceSite.Ecommerce.website.domain.AccountStatus;
import com.EcommerceSite.Ecommerce.website.entity.SellerEntity;
import com.EcommerceSite.Ecommerce.website.exceptions.SellerException;
import com.EcommerceSite.Ecommerce.website.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AdminController {

    @Autowired
    private SellerService sellerService;

    @PatchMapping("/seller/{id}/status/{status}")
    public ResponseEntity<SellerEntity> updateSellerStatus(
            @PathVariable Long id,
            @PathVariable AccountStatus status
    ) throws Exception {

        SellerEntity updatedSeller = sellerService.updateSellerAccountStatus(id, status);
        return ResponseEntity.ok(updatedSeller);
    }
}
