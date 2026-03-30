package com.EcommerceSite.Ecommerce.website.service;

import com.EcommerceSite.Ecommerce.website.domain.USER_ROLE;
import com.EcommerceSite.Ecommerce.website.request.LoginRequest;
import com.EcommerceSite.Ecommerce.website.responseDto.AuthResponse;
import com.EcommerceSite.Ecommerce.website.responseDto.SignUpRequest;

public interface AuthService {

    void sendLoginOtp(String email, USER_ROLE role) throws Exception;
    String createUser(SignUpRequest req) throws Exception;

    AuthResponse signing(LoginRequest req) throws Exception;
}
