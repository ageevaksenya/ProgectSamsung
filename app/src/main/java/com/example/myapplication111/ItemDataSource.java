package com.example.myapplication111;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class ItemDataSource extends PageKeyedDataSource<Integer,UsersB> {

    public static final int PAGE_SIZE = 10;
    private static final int FIRST_PAGE = 1;




    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer,UsersB> loadCallback) {
        Call<ResponseLeaders> call = MyServer.service.users("Users",params.key, PAGE_SIZE);
        call.enqueue(new Callback<ResponseLeaders>() {
            @Override
            public void onResponse(Call<ResponseLeaders> call, Response<ResponseLeaders> response) {

                if (response.body()!=null) {
                    Integer key = response.body().has_more ? params.key + 1 : null;
                    loadCallback.onResult(response.body().list,key );
                }

            }

            @Override
            public void onFailure(Call<ResponseLeaders> call, Throwable t) {


            }


        });

    }

    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer,UsersB> loadCallback) {
        Call<ResponseLeaders> call = MyServer.service.users("Users",params.key, PAGE_SIZE);
        call.enqueue(new Callback<ResponseLeaders>() {
            @Override
            public void onResponse(Call<ResponseLeaders> call, Response<ResponseLeaders> response) {
                Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
                if (response.body()!=null) {

                    loadCallback.onResult(response.body().list,adjacentKey );
                }

            }

            @Override
            public void onFailure(Call<ResponseLeaders> call, Throwable t) {


            }


        });
    }

    public void loadInitial(@NonNull LoadInitialParams<Integer> loadInitialParams, @NonNull LoadInitialCallback<Integer,UsersB> loadInitialCallback) {
        Call<ResponseLeaders > call = MyServer.service.users("Users",FIRST_PAGE, PAGE_SIZE);
        call.enqueue(new Callback<ResponseLeaders>() {
            @Override
            public void onResponse(Call<ResponseLeaders> call, Response<ResponseLeaders> response) {
                if (response.body()!=null) {
                    loadInitialCallback.onResult(response.body().list, null, FIRST_PAGE + 1);
                }

            }

            @Override
            public void onFailure(Call<ResponseLeaders> call, Throwable t) {


            }


        });

    }


}

