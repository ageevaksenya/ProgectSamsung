package com.example.myapplication111;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {
    @POST("/")
    @FormUrlEncoded
    Call<TypeReponse> fetchUser(@Field("type") String  type, @Field("name") String name, @Field("email") String email, @Field("password") String password, @Field("image") byte[] image);
    @POST("/")
    @FormUrlEncoded
    Call<List<Securities>> sec(@Field("type") String  type, @Field("table") String table);
    @POST("/")
    @FormUrlEncoded
    Call<ResponseLeaders> users(@Field("type") String  type,@Query("page")int page,@Query("pagesize")int pagesize);
    @POST("/")
    @FormUrlEncoded
    Call<List<String>> users_list(@Field("type") String  type);
    @POST("/")
    @FormUrlEncoded
    Call<MyData> AUser(@Field("type") String  type, @Field("email") String email, @Field("password") String password);
    @POST("/")
    @FormUrlEncoded
    Call<Tk> purchase (@Field("type")String type,@Field("token") String token,@Field("amount") int  amount);
    @GET("/")
    @FormUrlEncoded
    Call<Tk> sale (@Field("type")String type,@Field("token") String token,@Field("amount") int amount);
    @POST("/")
    @FormUrlEncoded
    Call<Tk> photo (@Field("type")String type,@Field("token") String token,@Field("photo") byte[] photo);
    @GET("/")
    @FormUrlEncoded
    Call<Tk> token (@Field("type")String type);
    @GET("/")
    @FormUrlEncoded
    Call<String> price (@Field("type")String type,@Field("name")String name,@Field("comp")String comp);
    @GET("/")
    @FormUrlEncoded
    Call<Integer> update_purchase (@Field("type")String type,@Field("name")String name,@Field("comp")String comp);
//    @Multipart

}
