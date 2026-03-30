package com.EcommerceSite.Ecommerce.website.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Entity
public class DealEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private Integer discount;

    public DealEntity(Long id, Integer discount, HomeCategory category) {
        this.id = id;
        this.discount = discount;
        this.category = category;
    }
    public DealEntity(){


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DealEntity that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getDiscount(), that.getDiscount()) && Objects.equals(getCategory(), that.getCategory());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDiscount(), getCategory());
    }

    @OneToOne
    private HomeCategory category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public HomeCategory getCategory() {
        return category;
    }

    public void setCategory(HomeCategory category) {
        this.category = category;
    }
}
