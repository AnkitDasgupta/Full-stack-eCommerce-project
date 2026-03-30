package com.EcommerceSite.Ecommerce.website.service.impl;

import com.EcommerceSite.Ecommerce.website.configuration.JwtProvider;
import com.EcommerceSite.Ecommerce.website.entity.UserEntity;
import com.EcommerceSite.Ecommerce.website.repository.UserRepository;
import com.EcommerceSite.Ecommerce.website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    public UserEntity findUserByJwtToken(String jwt) throws Exception {

        String email = jwtProvider.getEmailFromJwtToken(jwt);

        return this.findUserByEmail(email);

    }
    @Override
    public UserEntity findUserByEmail(String email) throws Exception {

        UserEntity user = userRepository.findByEmail(email);
        if(user == null){

            throw new Exception("user not found with email - "+email);
        }
        return user;
    }
}
