package com.example.mohamed.myapplication.models;

public class second {


    private String Id;
    private String Name;
    private String Image;
    private String price;

    public second() {
    }

    public second(String id, String name, String image, String price) {
        Id = id;
        Name = name;
        Image = image;
        this.price = price;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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
