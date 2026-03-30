package com.EcommerceSite.Ecommerce.website.cntroller;


import com.EcommerceSite.Ecommerce.website.entity.CartEntity;
import com.EcommerceSite.Ecommerce.website.entity.CartItemEntity;
import com.EcommerceSite.Ecommerce.website.entity.ProductEntity;
import com.EcommerceSite.Ecommerce.website.entity.UserEntity;
import com.EcommerceSite.Ecommerce.website.exceptions.ProductException;
import com.EcommerceSite.Ecommerce.website.request.AddItemRequest;
import com.EcommerceSite.Ecommerce.website.responseDto.ApiResponse;
import com.EcommerceSite.Ecommerce.website.service.CartItemService;
import com.EcommerceSite.Ecommerce.website.service.CartService;
import com.EcommerceSite.Ecommerce.website.service.ProductService;
import com.EcommerceSite.Ecommerce.website.service.UserService;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<CartEntity> findUserCartHandler(
            @RequestHeader("Authorization") String jwt) throws Exception {

        UserEntity user = userService.findUserByJwtToken(jwt);

        CartEntity cart = cartService.findUserCart(user);

        return new ResponseEntity<CartEntity>(cart, HttpStatus.OK);

    }

    @PutMapping("/add")
    public ResponseEntity<CartItemEntity> addItemToCart(
            @RequestBody AddItemRequest req,
            @RequestHeader("Authorization") String jwt)
            throws ProductException, Exception {

        UserEntity user = userService.findUserByJwtToken(jwt);

        ProductEntity product = productService.findProductById(req.getProductId());

        CartItemEntity item = cartService.addCartItem(
                user,
                product,
                req.getSize(),
                req.getQuantity()
        );

        ApiResponse res = new ApiResponse();
        res.setMessage("Item Added To Cart Successfully");

        return new ResponseEntity<>(item, HttpStatus.ACCEPTED);

    }

    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItemHandler(
            @PathVariable Long cartItemId,
            @RequestHeader("Authorization") String jwt) throws Exception {

        UserEntity user = userService.findUserByJwtToken(jwt);

        cartItemService.removeCartItem(user.getId(), cartItemId);

        ApiResponse res = new ApiResponse();
        res.setMessage("Item Remove From Cart");

        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @PutMapping("/item/{cartItemId}")
    public ResponseEntity<CartItemEntity> updateCartItemHandler(
            @PathVariable Long cartItemId,
            @RequestBody CartItemEntity cartItem,
            @RequestHeader("Authorization") String jwt)
            throws Exception {

        UserEntity user = userService.findUserByJwtToken(jwt);

        CartItemEntity updatedCartItem = null;

        if (cartItem.getQuantity() > 0) {
            updatedCartItem = cartItemService.updateCartItem(
                    user.getId(),
                    cartItemId,
                    cartItem
            );
        }

        return new ResponseEntity<>(updatedCartItem, HttpStatus.ACCEPTED);
    }
}
