package me.leo.api;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

/**
 * Created by leo on 9/10/17.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductVO {
    private String id;
    private String name;
    private String sku;
    private String description;
    private float price;

    private List<Review> reviews;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
