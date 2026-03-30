package com.EcommerceSite.Ecommerce.website.service.impl;

import com.EcommerceSite.Ecommerce.website.configuration.JwtProvider;
import com.EcommerceSite.Ecommerce.website.domain.USER_ROLE;
import com.EcommerceSite.Ecommerce.website.entity.CartEntity;
import com.EcommerceSite.Ecommerce.website.entity.SellerEntity;
import com.EcommerceSite.Ecommerce.website.entity.UserEntity;
import com.EcommerceSite.Ecommerce.website.entity.VerificationCode;
import com.EcommerceSite.Ecommerce.website.repository.CartRepository;
import com.EcommerceSite.Ecommerce.website.repository.SellerRepository;
import com.EcommerceSite.Ecommerce.website.repository.UserRepository;
import com.EcommerceSite.Ecommerce.website.repository.VerificationCodeRepository;
import com.EcommerceSite.Ecommerce.website.request.LoginRequest;
import com.EcommerceSite.Ecommerce.website.responseDto.AuthResponse;
import com.EcommerceSite.Ecommerce.website.responseDto.SignUpRequest;
import com.EcommerceSite.Ecommerce.website.service.AuthService;
import com.EcommerceSite.Ecommerce.website.service.EmailService;
import com.EcommerceSite.Ecommerce.website.utils.OtpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private VerificationCodeRepository verificationCodeRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private CustomUserServiceImpl customUserService;
    @Autowired
    private SellerRepository sellerRepository;

    @Override
    public void sendLoginOtp(String email, USER_ROLE role) throws Exception {
        String SIGNING_PREFIX = "signing_";


        if (email.startsWith(SIGNING_PREFIX)) {

            email = email.substring(SIGNING_PREFIX.length());

            if(role.equals(USER_ROLE.ROLE_SELLER));

            SellerEntity seller = sellerRepository.findByEmail(email);
            if(seller==null){

                throw new Exception("seller not found");
            }
            else{
                UserEntity user = userRepository.findByEmail(email);
                if (user == null) {

                    throw new Exception("User not exist with provided email");

                }
            }

        }

        VerificationCode isExist = verificationCodeRepository.findByEmail(email);

        if (isExist != null) {

            verificationCodeRepository.delete(isExist);
        }

        String otp = OtpUtils.generateOtp();

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(email);

        verificationCodeRepository.save(verificationCode);

        String subject = "OnedooR bazaar login/signup otp";
        String text = "your login/signup otp is - " + otp;

        emailService.sendVerificationOtpEmail(email, otp, subject, text);

    }

    @Override
    public String createUser(SignUpRequest req) throws Exception {

        VerificationCode verificationCode = verificationCodeRepository.findByEmail(req.getEmail());

        if (verificationCode == null || !verificationCode.getOtp().equals(req.getOtp())) {

            throw new Exception("wrong otp...");
        }

        UserEntity user = userRepository.findByEmail(req.getEmail());

        if (user == null) {

            UserEntity createdUser = new UserEntity();
            createdUser.setEmail(req.getEmail());
            createdUser.setFullName(req.getFullName());
            createdUser.setRole(USER_ROLE.ROLE_CUSTOMER);
            createdUser.setMobileNo("9614529792");
            createdUser.setPassword(passwordEncoder.encode(req.getOtp()));

            user = userRepository.save(createdUser);

            CartEntity cart = new CartEntity();
            cart.setUser(user);
            cartRepository.save(cart);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(USER_ROLE.ROLE_CUSTOMER.toString()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(req.getEmail(), null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);


        return jwtProvider.generateToken(authentication);
    }

    @Override
    public AuthResponse signing(LoginRequest req) throws Exception {

        String username = req.getEmail();
        String otp = req.getOtp();

        Authentication authentication = authenticate(username, otp);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtProvider.generateToken(authentication);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setJwt(token);
        authResponse.setMessage("Login success");


        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        String roleName = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();

        authResponse.setRole(USER_ROLE.valueOf(roleName));

        return authResponse;

    }

    private Authentication authenticate(String username, String otp) throws Exception {


        UserDetails userDetails = customUserService.loadUserByUsername(username);

        String SELLER_PREFIX = "seller_";


        if(username.startsWith(SELLER_PREFIX)){

       username = username.substring(SELLER_PREFIX.length());

        }



        if (userDetails == null) {

            throw new BadCredentialsException("invalid username");


        }
        VerificationCode verificationCode = verificationCodeRepository.findByEmail(username);

        if (verificationCode == null || !verificationCode.getOtp().equals(otp)) {

            throw new Exception("wrong otp");


        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
