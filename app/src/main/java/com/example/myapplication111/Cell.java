package com.example.myapplication111;



import java.io.Serializable;

public class Cell implements Serializable {
    private long id;
    private String name;
    private String company;
    private byte[] image;
    private int price;


    public Cell (long id, String name, String company,byte[] image,int price) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.image = image;
        this.price=price;

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCompany() {
        return company;
    }

    public byte[] getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }
}
