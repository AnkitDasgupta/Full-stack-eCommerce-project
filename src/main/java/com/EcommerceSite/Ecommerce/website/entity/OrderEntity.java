package com.EcommerceSite.Ecommerce.website.entity;

import com.EcommerceSite.Ecommerce.website.domain.OrderStatus;
import com.EcommerceSite.Ecommerce.website.domain.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long sellerId;

    @ManyToOne
    private UserEntity user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderEntity that)) return false;
        return Double.compare(getTotalMrpPrice(), that.getTotalMrpPrice()) == 0 && getTotalItem() == that.getTotalItem() && Objects.equals(getId(), that.getId()) && Objects.equals(getSellerId(), that.getSellerId()) && Objects.equals(getUser(), that.getUser()) && Objects.equals(getOrderItem(), that.getOrderItem()) && Objects.equals(getShippingAddress(), that.getShippingAddress()) && Objects.equals(getPaymentDetails(), that.getPaymentDetails()) && Objects.equals(getTotalSellingPrice(), that.getTotalSellingPrice()) && Objects.equals(getDiscount(), that.getDiscount()) && getOrderStatus() == that.getOrderStatus() && getPaymentStatus() == that.getPaymentStatus() && Objects.equals(getOrderDate(), that.getOrderDate()) && Objects.equals(getDeliverDate(), that.getDeliverDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSellerId(), getUser(), getOrderItem(), getShippingAddress(), getPaymentDetails(), getTotalMrpPrice(), getTotalSellingPrice(), getDiscount(), getOrderStatus(), getTotalItem(), getPaymentStatus(), getOrderDate(), getDeliverDate());
    }

    @OneToMany(
            mappedBy = "order",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<OrderItemEntity> orderItem = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "payment_order_id")
    private PaymentOrder paymentOrder;

    @ManyToOne
    private AddressEntity shippingAddress;

    @Embedded
    private PaymentDetails paymentDetails = new PaymentDetails();

    private double totalMrpPrice;

    private Integer totalSellingPrice;

    private Integer discount;

    private OrderStatus orderStatus;


    private int totalItem;

    private PaymentStatus paymentStatus = PaymentStatus.PENDING;


    private LocalDateTime orderDate = LocalDateTime.now();

    private LocalDateTime deliverDate = orderDate.plusDays(7);


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public List<OrderItemEntity> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(List<OrderItemEntity> orderItem) {
        this.orderItem = orderItem;
    }

    public AddressEntity getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(AddressEntity shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public double getTotalMrpPrice() {
        return totalMrpPrice;
    }

    public void setTotalMrpPrice(double totalMrpPrice) {
        this.totalMrpPrice = totalMrpPrice;
    }

    public Integer getTotalSellingPrice() {
        return totalSellingPrice;
    }

    public void setTotalSellingPrice(Integer totalSellingPrice) {
        this.totalSellingPrice = totalSellingPrice;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getTotalItem() {
        return totalItem;
    }

    public void setTotalItem(int totalItem) {
        this.totalItem = totalItem;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDateTime getDeliverDate() {
        return deliverDate;
    }

    public void setDeliverDate(LocalDateTime deliverDate) {
        this.deliverDate = deliverDate;
    }
}
