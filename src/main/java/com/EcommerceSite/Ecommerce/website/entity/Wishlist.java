package com.EcommerceSite.Ecommerce.website.entity;

import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.User;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name="user_id")
    private UserEntity user;

    @ManyToMany
    private Set<ProductEntity> products = new HashSet<>();

    public Wishlist() {

    }

    public Wishlist(Long id, UserEntity user, Set<ProductEntity> products) {
        this.id = id;
        this.user = user;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Set<ProductEntity> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductEntity> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Wishlist wishlist)) return false;
        return Objects.equals(getId(), wishlist.getId()) && Objects.equals(getUser(), wishlist.getUser()) && Objects.equals(getProducts(), wishlist.getProducts());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getProducts());
    }
}
