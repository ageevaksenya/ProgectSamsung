package com.example.myapplication111;




import java.io.Serializable;

public class Cell2 implements Serializable {
    private long id;
    private String name;
    private String company;
    private byte[] image;
    private int price;
    private int count;


    public Cell2 (long id, String name, String company,byte[] image,int price,int count) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.image = image;
        this.price=price;
        this.count=count;

    }
    public int getCount(){return count;}

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
