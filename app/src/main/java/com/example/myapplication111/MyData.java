package com.example.myapplication111;

import com.google.gson.annotations.SerializedName;

public class MyData {
    @SerializedName("cash")
    private static int my_purse;
    @SerializedName("name")
    static String my_name;
    @SerializedName("photo")
    static byte[] my_image;
    @SerializedName("token")
    static private String token;
    @SerializedName("id")
    static private String id;

    public static void setMy_purse(int my_purse) {
        MyData.my_purse = my_purse;
    }

    public static int getMy_purse() {
        return my_purse;
    }

    public static String getMy_name() {
        return my_name;
    }

    public byte[] getMy_image() {
        return my_image;
    }

    public static void setMy_image(byte[] my_image) {
        MyData.my_image = my_image;
    }

    public static void setMy_name(String my_name) {
        MyData.my_name = my_name;
    }

    public String getId() {
        return id;
    }

    public static void setId(String id) {
        MyData.id = id;
    }

    public static  void setToken(String token) {
        MyData.token = token;
    }

    public static String getToken() {
        return token;
    }


    public MyData(String my_name, byte[] my_image, int my_purse) {
        MyData.my_name = my_name;
        MyData.my_image =my_image;
        MyData.my_purse =my_purse;
    }
    public MyData() { }
}
