package com.ajmalyousufza.mygroceryshoppingcart.models;

public class NavCategoryDetailedModel {

    String name;
    String price;
    String type;
    String image_url;

    public NavCategoryDetailedModel() {
    }

    public NavCategoryDetailedModel(String name, String price, String type, String image_url) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }
}
