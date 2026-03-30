package com.EcommerceSite.Ecommerce.website.entity;

import com.EcommerceSite.Ecommerce.website.domain.HomeCategorySection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class HomeCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String image;

    private String categoryId;

    private HomeCategorySection section;

    public Long getId() {
        return id;
    }

    public HomeCategory(Long id, String name, String image, String categoryId, HomeCategorySection section) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.categoryId = categoryId;
        this.section = section;
    }
    public HomeCategory(){


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HomeCategory category)) return false;
        return Objects.equals(getId(), category.getId()) && Objects.equals(getName(), category.getName()) && Objects.equals(getImage(), category.getImage()) && Objects.equals(getCategoryId(), category.getCategoryId()) && getSection() == category.getSection();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getImage(), getCategoryId(), getSection());
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public HomeCategorySection getSection() {
        return section;
    }

    public void setSection(HomeCategorySection section) {
        this.section = section;
    }
}
