package com.EcommerceSite.Ecommerce.website.cntroller;

import com.EcommerceSite.Ecommerce.website.domain.AccountStatus;
import com.EcommerceSite.Ecommerce.website.entity.SellerEntity;
import com.EcommerceSite.Ecommerce.website.entity.SellerReport;
import com.EcommerceSite.Ecommerce.website.entity.VerificationCode;
import com.EcommerceSite.Ecommerce.website.exceptions.SellerException;
import com.EcommerceSite.Ecommerce.website.repository.VerificationCodeRepository;
import com.EcommerceSite.Ecommerce.website.request.LoginOtpRequest;
import com.EcommerceSite.Ecommerce.website.request.LoginRequest;
import com.EcommerceSite.Ecommerce.website.responseDto.ApiResponse;
import com.EcommerceSite.Ecommerce.website.responseDto.AuthResponse;
import com.EcommerceSite.Ecommerce.website.service.AuthService;
import com.EcommerceSite.Ecommerce.website.service.EmailService;
import com.EcommerceSite.Ecommerce.website.service.SellerReportService;
import com.EcommerceSite.Ecommerce.website.service.SellerService;
import com.EcommerceSite.Ecommerce.website.utils.OtpUtils;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sellers")
public class SellerController {
    @Autowired
    private SellerService sellerService;
    @Autowired
    private VerificationCodeRepository verificationCodeRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private SellerReportService sellerReportService;

@PostMapping("/login")
    public ResponseEntity<AuthResponse> loginSeller (
            @RequestBody LoginRequest req
            ) throws Exception {

        String otp = req.getOtp();
        String email = req.getEmail();


        req.setEmail("seller_"+email);
        AuthResponse authResponse = authService.signing(req);
        return ResponseEntity.ok(authResponse);
    }

    @PatchMapping("/verify/{otp}")
    public ResponseEntity<SellerEntity> verifySellerEmail(
            @PathVariable String otp) throws Exception {

        VerificationCode verificationCode = verificationCodeRepository.findByOtp(otp);

        if (verificationCode == null || !verificationCode.getOtp().equals(otp)) {
            throw new Exception("Wrong otp...");
        }

        SellerEntity seller = sellerService.verifyEmail(verificationCode.getEmail(), otp);

        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<SellerEntity> createSeller(
            @RequestBody SellerEntity seller) throws Exception, MessagingException {

        SellerEntity savedSeller = sellerService.createSeller(seller);

        String otp = OtpUtils.generateOtp();

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setOtp(otp);
        verificationCode.setEmail(seller.getEmail());

        verificationCodeRepository.save(verificationCode);

        String subject = "OnedooR Shoping Email Verification Code";
        String text = "Welcome to OnedooR, verify your account using this link ";
        String frontend_url = "http://localhost:3000/verify-seller/";

        emailService.sendVerificationOtpEmail(
                seller.getEmail(),
                verificationCode.getOtp(),
                subject,
                text + frontend_url + verificationCode.getOtp()
        );

        return new ResponseEntity<>(savedSeller, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SellerEntity> getSellerById(@PathVariable Long id) throws SellerException {
        SellerEntity seller = sellerService.getSellerById(id);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @GetMapping("/profile")
    public ResponseEntity<SellerEntity> getSellerByJwt(
            @RequestHeader("Authorization") String jwt) throws Exception {

        SellerEntity seller = sellerService.getSellerProfile(jwt);

        return new ResponseEntity<>(seller, HttpStatus.OK);

    }

    @GetMapping("/report")
    public ResponseEntity<SellerReport> getSellerReport(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {

        SellerEntity seller = sellerService.getSellerProfile(jwt);
        SellerReport report = sellerReportService.getSellerReport(seller);

        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @GetMapping("/api")
    public ResponseEntity<List<SellerEntity>> getAllSellers(
            @RequestParam(required = false) AccountStatus status) {

        return ResponseEntity.ok(sellerService.getAllSeller(status));
    }

    @PatchMapping
    public ResponseEntity<SellerEntity> updateSeller(
            @RequestHeader("Authorization") String jwt,
            @RequestBody SellerEntity seller) throws Exception {

        SellerEntity profile = sellerService.getSellerProfile(jwt);
        SellerEntity updatedSeller =
                sellerService.updateSeller(profile.getId(), seller);

        return ResponseEntity.ok(updatedSeller);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeller(@PathVariable Long id) throws Exception {

        sellerService.deleteSeller(id);

        return ResponseEntity.noContent().build();
    }
}
