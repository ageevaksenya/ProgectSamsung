package com.example.myapplication111;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

public class ItemDataSourceFactory extends DataSource.Factory {


    private MutableLiveData<PageKeyedDataSource<Integer, UsersB>> itemLiveDataSource = new MutableLiveData<>();

    @Override
    public DataSource<Integer, UsersB> create() {

        ItemDataSource itemDataSource = new ItemDataSource();


        itemLiveDataSource.postValue(itemDataSource);


        return itemDataSource;
    }



    public MutableLiveData<PageKeyedDataSource<Integer, UsersB>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}