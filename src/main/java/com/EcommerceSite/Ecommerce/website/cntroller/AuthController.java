package com.EcommerceSite.Ecommerce.website.cntroller;

import com.EcommerceSite.Ecommerce.website.domain.USER_ROLE;
import com.EcommerceSite.Ecommerce.website.repository.UserRepository;
import com.EcommerceSite.Ecommerce.website.request.LoginOtpRequest;
import com.EcommerceSite.Ecommerce.website.request.LoginRequest;
import com.EcommerceSite.Ecommerce.website.responseDto.ApiResponse;
import com.EcommerceSite.Ecommerce.website.responseDto.AuthResponse;
import com.EcommerceSite.Ecommerce.website.responseDto.SignUpRequest;
import com.EcommerceSite.Ecommerce.website.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody SignUpRequest req) throws Exception {

     String jwt = authService.createUser(req);
        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setMessage("register success");
        res.setRole(USER_ROLE.ROLE_CUSTOMER);

       return ResponseEntity.ok(res);

    }

    @PostMapping("/sent/login-signup-0tp")
    public ResponseEntity<ApiResponse> sentOtpHandler(
            @RequestBody LoginOtpRequest req) throws Exception {

        authService.sendLoginOtp(req.getEmail(),req.getRole());

        ApiResponse res = new ApiResponse();

        res.setMessage("otp sent successfully");


        return ResponseEntity.ok(res);

    }

    @PostMapping("/signing")
    public ResponseEntity<AuthResponse> loginHandler(
            @RequestBody LoginRequest req) throws Exception {


        AuthResponse authResponse = authService.signing(req);


        return ResponseEntity.ok(authResponse);

    }


}
