package com.example.myapplication111;

import android.content.ContentResolver;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;

import androidx.fragment.app.ListFragment;

import com.google.gson.annotations.SerializedName;

public class Tk  {
    @SerializedName("token")
    private String token;

    public  String getToken() {
        return token;
    }

    public  void setToken(String token) {
        this.token = token;
    }


}
