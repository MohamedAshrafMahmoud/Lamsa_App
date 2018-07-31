package com.example.mohamed.myapplication.models;

public class third {

    private String Image;
    private String Name;
    private String price;

    public third(String image, String name, String price) {
        Image = image;
        Name = name;
        this.price = price;
    }


    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
