package com.ajmalyousufza.mygroceryshoppingcart.models;

public class ViewAllModel {
    String name;
    String description;
    String rating;
    int price;
    String image_url;
    String type;

    public ViewAllModel() {
    }

    public ViewAllModel(String name, String description, String rating, int price, String image_url, String type) {
        this.name = name;
        this.description = description;
        this.rating = rating;
        this.price = price;
        this.image_url = image_url;
        this.type = type;
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

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
