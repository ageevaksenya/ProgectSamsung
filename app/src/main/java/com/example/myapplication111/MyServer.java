package com.example.myapplication111;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class MyServer {


    static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://5.189.204.10:1010")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    static UserService service = retrofit.create(UserService.class);



}










