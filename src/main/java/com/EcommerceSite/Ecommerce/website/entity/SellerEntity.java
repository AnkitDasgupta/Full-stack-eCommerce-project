package com.EcommerceSite.Ecommerce.website.entity;

import com.EcommerceSite.Ecommerce.website.domain.AccountStatus;
import com.EcommerceSite.Ecommerce.website.domain.USER_ROLE;
import jakarta.persistence.*;

@Entity
public class SellerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sellerName;
    private String mobile;
    @Column(unique = true,nullable = false)
    private String email;
    private String password;
    @Embedded
    private BusinessDetails businessDetails = new BusinessDetails();
    @Embedded
    private BankDetails bankDetails = new BankDetails();
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pickUpAddress")
    private AddressEntity pickUpAddress = new AddressEntity();
    private String gstIn;
    private USER_ROLE role = USER_ROLE.ROLE_SELLER;
    private boolean isEmailVerified = false;
    private AccountStatus accountStatus = AccountStatus.PENDING_VERIFICATION;


    public Long getId() {
        return id;
    }

    public String getSellerName() {
        return sellerName;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public BusinessDetails getBusinessDetails() {
        return businessDetails;
    }

    public BankDetails getBankDetails() {
        return bankDetails;
    }

    public AddressEntity getPickUpAddress() {
        return pickUpAddress;
    }

    public String getGstIn() {
        return gstIn;
    }

    public USER_ROLE getRole() {
        return role;
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setBusinessDetails(BusinessDetails businessDetails) {
        this.businessDetails = businessDetails;
    }

    public void setBankDetails(BankDetails bankDetails) {
        this.bankDetails = bankDetails;
    }

    public void setPickUpAddress(AddressEntity pickUpAddress) {
        this.pickUpAddress = pickUpAddress;
    }

    public void setGstIn(String gstIn) {
        this.gstIn = gstIn;
    }

    public void setRole(USER_ROLE role) {
        this.role = role;
    }

    public void setEmailVerified(boolean emailVerified) {
        isEmailVerified = emailVerified;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public SellerEntity(Long id, String sellerName, String mobile, String email, String password, BusinessDetails businessDetails, BankDetails bankDetails, AddressEntity pickUpAddress, String gstIn, USER_ROLE role, boolean isEmailVerified, AccountStatus accountStatus) {
        this.id = id;
        this.sellerName = sellerName;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
        this.businessDetails = businessDetails;
        this.bankDetails = bankDetails;
        this.pickUpAddress = pickUpAddress;
        this.gstIn = gstIn;
        this.role = role;
        this.isEmailVerified = isEmailVerified;
        this.accountStatus = accountStatus;



    }

   public SellerEntity(){


    }
}
