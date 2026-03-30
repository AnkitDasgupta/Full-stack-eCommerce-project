package com.EcommerceSite.Ecommerce.website.cntroller;

import com.EcommerceSite.Ecommerce.website.entity.DealEntity;
import com.EcommerceSite.Ecommerce.website.responseDto.ApiResponse;
import com.EcommerceSite.Ecommerce.website.service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/deals")
public class DealController {

    @Autowired
    private DealService dealService;

    @PostMapping
    public ResponseEntity<DealEntity> createDeals(
            @RequestBody DealEntity deals
    ) {
        DealEntity createdDeals = dealService.createDeal(deals);

        return new ResponseEntity<>(createdDeals, HttpStatus.ACCEPTED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DealEntity> updateDeal(
            @PathVariable Long id,
            @RequestBody DealEntity deal
    ) throws Exception {

        DealEntity updatedDeal = dealService.updateDeal(deal, id);
        return ResponseEntity.ok(updatedDeal);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteDeals(
            @PathVariable Long id
    ) throws Exception {

        dealService.deleteDeal(id);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Deal deleted");


        return new ResponseEntity<>(apiResponse, HttpStatus.ACCEPTED);
    }

}
