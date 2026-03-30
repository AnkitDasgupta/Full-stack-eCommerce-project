package com.EcommerceSite.Ecommerce.website.entity;

import com.EcommerceSite.Ecommerce.website.domain.USER_ROLE;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
    private String password;

    private String email;

    private String fullName;

    private String mobileNo;

    private USER_ROLE role = USER_ROLE.ROLE_CUSTOMER;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AddressEntity> address = new HashSet<>();

    @OneToMany
    private Set<PaymentOrder> paymentOrders = new HashSet<>();

    @ManyToMany
    @JsonIgnore
    private Set<CouponEntity> usedCoupons = new HashSet<>();




    public UserEntity(Long id, String password, String email, String fullName, String mobileNo, USER_ROLE role, Set<AddressEntity> address, Set<CouponEntity> usedCoupons) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.fullName = fullName;
        this.mobileNo = mobileNo;
        this.role = role;
        this.address = address;
        this.usedCoupons = usedCoupons;
    }

    public UserEntity(){


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getPassword(), that.getPassword()) && Objects.equals(getEmail(), that.getEmail()) && Objects.equals(getFullName(), that.getFullName()) && Objects.equals(getMobileNo(), that.getMobileNo()) && getRole() == that.getRole() && Objects.equals(getAddress(), that.getAddress()) && Objects.equals(getUsedCoupons(), that.getUsedCoupons());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPassword(), getEmail(), getFullName(), getMobileNo(), getRole(), getAddress(), getUsedCoupons());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public USER_ROLE getRole() {
        return role;
    }

    public void setRole(USER_ROLE role) {
        this.role = role;
    }

    public Set<AddressEntity> getAddress() {
        return address;
    }

    public void setAddress(Set<AddressEntity> address) {
        this.address = address;
    }

    public Set<CouponEntity> getUsedCoupons() {
        return usedCoupons;
    }

    public void setUsedCoupons(Set<CouponEntity> usedCoupons) {
        this.usedCoupons = usedCoupons;
    }
}
