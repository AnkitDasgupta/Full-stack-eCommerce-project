package com.EcommerceSite.Ecommerce.website.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class SellerReport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "seller_id")
    private SellerEntity seller;

    private Long  totalEarning=0L;

    private Long  totalSales=0L;

    private Long  totalRefunds = 0L;

    private Long totalTax = 0L;


    private Long  netEarnings=0L;


    private Integer  totalOrders =0;

    private Integer  canceledOrders=0;

    private Integer  totalTransactions=0;

    public SellerReport() {

    }

    public SellerReport(Long id, SellerEntity seller, Long totalEarning, Long totalSales, Long totalRefunds, Long totalTax, Long netEarnings, Integer totalOrders, Integer canceledOrders, Integer totalTransactions) {
        this.id = id;
        this.seller = seller;
        this.totalEarning = totalEarning;
        this.totalSales = totalSales;
        this.totalRefunds = totalRefunds;
        this.totalTax = totalTax;
        this.netEarnings = netEarnings;
        this.totalOrders = totalOrders;
        this.canceledOrders = canceledOrders;
        this.totalTransactions = totalTransactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SellerReport that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getSeller(), that.getSeller()) && Objects.equals(getTotalEarning(), that.getTotalEarning()) && Objects.equals(getTotalSales(), that.getTotalSales()) && Objects.equals(getTotalRefunds(), that.getTotalRefunds()) && Objects.equals(getTotalTax(), that.getTotalTax()) && Objects.equals(getNetEarnings(), that.getNetEarnings()) && Objects.equals(getTotalOrders(), that.getTotalOrders()) && Objects.equals(getCanceledOrders(), that.getCanceledOrders()) && Objects.equals(getTotalTransactions(), that.getTotalTransactions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getSeller(), getTotalEarning(), getTotalSales(), getTotalRefunds(), getTotalTax(), getNetEarnings(), getTotalOrders(), getCanceledOrders(), getTotalTransactions());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SellerEntity getSeller() {
        return seller;
    }

    public void setSeller(SellerEntity seller) {
        this.seller = seller;
    }

    public Long getTotalEarning() {
        return totalEarning;
    }

    public void setTotalEarning(Long totalEarning) {
        this.totalEarning = totalEarning;
    }

    public Long getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Long totalSales) {
        this.totalSales = totalSales;
    }

    public Long getTotalRefunds() {
        return totalRefunds;
    }

    public void setTotalRefunds(Long totalRefunds) {
        this.totalRefunds = totalRefunds;
    }

    public Long getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(Long totalTax) {
        this.totalTax = totalTax;
    }

    public Long getNetEarnings() {
        return netEarnings;
    }

    public void setNetEarnings(Long netEarnings) {
        this.netEarnings = netEarnings;
    }

    public Integer getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(Integer totalOrders) {
        this.totalOrders = totalOrders;
    }

    public Integer getCanceledOrders() {
        return canceledOrders;
    }

    public void setCanceledOrders(Integer canceledOrders) {
        this.canceledOrders = canceledOrders;
    }

    public Integer getTotalTransactions() {
        return totalTransactions;
    }

    public void setTotalTransactions(Integer totalTransactions) {
        this.totalTransactions = totalTransactions;
    }
}
