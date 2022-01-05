package com.ajmalyousufza.mygroceryshoppingcart.models;

public class HomeCategory {
    String name;
    String type;
    String img_src;

    public HomeCategory() {
    }

    public HomeCategory(String name, String type, String img_src) {
        this.name = name;
        this.type = type;
        this.img_src = img_src;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg_src() {
        return img_src;
    }

    public void setImg_src(String img_src) {
        this.img_src = img_src;
    }
}
