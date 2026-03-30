package com.EcommerceSite.Ecommerce.website.service;

import com.EcommerceSite.Ecommerce.website.entity.UserEntity;

public interface UserService {

    UserEntity findUserByJwtToken(String jwt) throws Exception;
    UserEntity findUserByEmail(String email) throws Exception;


}
