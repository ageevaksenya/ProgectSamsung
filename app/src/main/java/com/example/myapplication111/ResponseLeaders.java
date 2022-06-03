package com.example.myapplication111;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseLeaders {
    @SerializedName("has_more")
    boolean has_more;
    @SerializedName("listUsers")
    List<UsersB> list;
}
