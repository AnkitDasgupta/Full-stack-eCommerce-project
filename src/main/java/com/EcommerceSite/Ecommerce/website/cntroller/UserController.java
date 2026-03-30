package com.EcommerceSite.Ecommerce.website.cntroller;

import com.EcommerceSite.Ecommerce.website.domain.USER_ROLE;
import com.EcommerceSite.Ecommerce.website.entity.UserEntity;
import com.EcommerceSite.Ecommerce.website.responseDto.AuthResponse;
import com.EcommerceSite.Ecommerce.website.responseDto.SignUpRequest;
import com.EcommerceSite.Ecommerce.website.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private UserService userService;

    @GetMapping("/users/profile")
    public ResponseEntity<UserEntity> createUserHandler(
            @RequestHeader("Authorization") String jwt) throws Exception {

    UserEntity user = userService.findUserByJwtToken(jwt);

        return ResponseEntity.ok(user);
    }
}
