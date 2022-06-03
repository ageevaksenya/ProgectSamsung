package com.example.myapplication111;

import com.google.gson.annotations.SerializedName;

public class TypeReponse {
    @SerializedName("type")
    String type;





    public TypeReponse(String type) {

        this.type=type;
    }
}
