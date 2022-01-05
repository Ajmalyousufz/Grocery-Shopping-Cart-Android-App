package com.ajmalyousufza.mygroceryshoppingcart.models;

public class RecommendedModel {

    String name;
    String description;
    String image_url;
    String rating;
    int price;

    public RecommendedModel() {
    }

    public RecommendedModel(String name, String description, String image_url, String rating, int price) {
        this.name = name;
        this.description = description;
        this.image_url = image_url;
        this.rating = rating;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
