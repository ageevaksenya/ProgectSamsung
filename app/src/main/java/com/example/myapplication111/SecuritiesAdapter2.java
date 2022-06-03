package com.example.myapplication111;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

class Securities2{
    String title="";
    int price=0;
    String idImage="";
    int count=0;


}
public class SecuritiesAdapter2 extends ArrayAdapter<Securities2> {

    public SecuritiesAdapter2(@NonNull Context context, List<Securities2> list) {
        super(context,  R.layout.list_item2, list);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        final Securities2 securities = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item2, null);
        }

// Заполняем адаптер
        ((TextView) convertView.findViewById(R.id.name)).setText(securities.title);
        ((TextView) convertView.findViewById(R.id.capital1)).setText(String.valueOf(securities.price));
        ((ImageView) convertView.findViewById(R.id.icons)).setImageResource(Integer.parseInt(securities.idImage));
        ((TextView) convertView.findViewById(R.id.count)).setText(String.valueOf(securities.count));
        return convertView;
    }
}
