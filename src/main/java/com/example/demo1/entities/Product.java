package com.example.demo1.entities;

import com.example.demo1.entities.categories.Category;
import com.example.demo1.entities.categories.ValidCategory;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.enterprise.context.RequestScoped;
import jakarta.validation.constraints.*;


import java.util.Date;
import java.util.Objects;

@RequestScoped
@JsonSerialize(using = ProductSerializer.class)
public class Product {
    @Min(value = 1, message = "Product number cant be less than one")
    private int productId = 1;
    @Size.List({
            @Size(min = 3, message = "Product name should have at least 3 characters"),
            @Size(max = 23, message = "Maybe a little bit less characters (max 23)")
    })
    private String productName;

    @NotNull
    @ValidCategory
    @JsonDeserialize
    private Category productCategory;
    @Min(value = 1, message = "Give a valid rating 1-10")
    @Max(value = 10, message = "Give a valid rating 1-10")
    private byte productRating;
    @JsonIgnore
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'@'HH:mm:ss")
    private Date productCreated = currentDate();
    @JsonIgnore
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'@'HH:mm:ss")
    private Date productModified = currentDate();

    public Product() {};

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'@'HH:mm:ss")
    private Date currentDate() {
        return new Date();
    }

    public Product(String name, Category category, byte rating) {
        this.productName = name;
        this.productCategory = category;
        this.productRating = rating;
    }

    public Product(String name, Category category, byte rating, Date created) {
        this.productName = name;
        this.productCategory = category;
        this.productRating = rating;
        this.productCreated = created;
        this.productModified = created;
    }

    public Date getCreatedDate() {
        return productCreated;
    }

    public Date getModifiedDate() {
        return productModified;
    }

    public String getProductName() {
        return productName;
    }

    public int getProductId() {
        return productId;
    }

    public void changeProductName(String newProductName) {
        this.productName = newProductName;
        setProductModified();
    }

    public void changeProductCategory(Category otherCategory) {
        this.productCategory = otherCategory;
        setProductModified();
    }

    public void setProductCategory(Category category) {
        this.productCategory = category;
    }

    public Category getProductCategory() {
        return productCategory;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public void changeProductRating(byte newRating) {
        this.productRating = newRating;
        setProductModified();
    }

    public byte getProductRating() {
        return productRating;
    }

    private void setProductModified() {
        this.productModified = currentDate();
    }

    @Override
    public String toString() {
        return productName + " " + productCategory + " " + productRating + " " + productCreated + " " + productModified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(productName, product.productName) && productCategory == product.productCategory && Objects.equals(productCreated, product.productCreated);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, productCategory, productCreated);
    }

    public void setProductRating(byte rating) {
        this.productRating = rating;
    }

    public void setProductName(String name) {
        this.productName = name;
    }


}
