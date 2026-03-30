package com.EcommerceSite.Ecommerce.website.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.User;

import java.util.Objects;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class VerificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String otp;
    private String email;
    @OneToOne
    @JoinColumn(name = "user_id",unique = true)
    private UserEntity user;
    @OneToOne
    @JoinColumn(name = "seller_id",unique = true)
    private SellerEntity seller;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public SellerEntity getSeller() {
        return seller;
    }

    public void setSeller(SellerEntity seller) {
        this.seller = seller;
    }

    public VerificationCode(Long id, String otp, String email, UserEntity user, SellerEntity seller) {
        this.id = id;
        this.otp = otp;
        this.email = email;
        this.user = user;
        this.seller = seller;
    }
    public VerificationCode(){


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VerificationCode that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getOtp(), that.getOtp()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getUser(), that.getUser()) && Objects.equals(getSeller(), that.getSeller());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOtp(), getEmail(), getUser(), getSeller());
    }
}
