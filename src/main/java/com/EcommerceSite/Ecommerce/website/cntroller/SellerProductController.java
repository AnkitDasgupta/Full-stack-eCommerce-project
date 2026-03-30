package com.EcommerceSite.Ecommerce.website.cntroller;

import com.EcommerceSite.Ecommerce.website.entity.ProductEntity;
import com.EcommerceSite.Ecommerce.website.entity.SellerEntity;
import com.EcommerceSite.Ecommerce.website.exceptions.ProductException;
import com.EcommerceSite.Ecommerce.website.exceptions.SellerException;
import com.EcommerceSite.Ecommerce.website.request.CreateProductRequest;
import com.EcommerceSite.Ecommerce.website.service.ProductService;
import com.EcommerceSite.Ecommerce.website.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sellers/products")
public class SellerProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private SellerService sellerService;

    @GetMapping("/seller")
    public ResponseEntity<List<ProductEntity>> getProductBySellerId(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        SellerEntity seller = sellerService.getSellerProfile(jwt);

        List<ProductEntity> products =
                productService.getProductBySellerId(seller.getId());

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductEntity> createProduct(

            @RequestBody CreateProductRequest request,
            @RequestHeader("Authorization") String jwt

    ) throws Exception {

        SellerEntity seller = sellerService.getSellerProfile(jwt);

        ProductEntity product = productService.createProduct(request, seller);

        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable Long productId
    ) {

        try {
            productService.deleteProduct(productId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ProductException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductEntity> updateProduct(
            @PathVariable Long productId,
            @RequestBody ProductEntity product
    ) {

        try {
            ProductEntity updatedProduct =
                    productService.updateProduct(productId, product);

            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);

        } catch (ProductException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
