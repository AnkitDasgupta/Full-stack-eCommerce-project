package com.EcommerceSite.Ecommerce.website.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private UserEntity customer;
    @OneToOne
    @JoinColumn(name  = "order_id")
    private OrderEntity order;
    @ManyToOne
    @JoinColumn(name = "seller_id")
    private SellerEntity seller;

    public Transaction(Long id, UserEntity customer, OrderEntity order, SellerEntity seller, LocalDateTime date) {
        this.id = id;
        this.customer = customer;
        this.order = order;
        this.seller = seller;
        this.date = date;
    }

    public Transaction() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transaction that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getCustomer(), that.getCustomer()) && Objects.equals(getOrder(), that.getOrder()) && Objects.equals(getSeller(), that.getSeller()) && Objects.equals(getDate(), that.getDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCustomer(), getOrder(), getSeller(), getDate());
    }

    private LocalDateTime date = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getCustomer() {
        return customer;
    }

    public void setCustomer(UserEntity customer) {
        this.customer = customer;
    }

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    public SellerEntity getSeller() {
        return seller;
    }

    public void setSeller(SellerEntity seller) {
        this.seller = seller;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
