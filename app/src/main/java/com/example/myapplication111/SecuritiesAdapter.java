package com.example.myapplication111;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

class Securities{
    String title="";
    String company="";
    int price=0;
    byte[] idImage;
    int id=0;


}
public class SecuritiesAdapter extends ArrayAdapter<Securities> {

    public SecuritiesAdapter(@NonNull Context context, List<Securities> list) {
        super(context,  R.layout.list_item, list);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        final Securities securities = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, null);
        }

// Заполняем адаптер
        ((TextView) convertView.findViewById(R.id.name)).setText(securities.title);
        ((TextView) convertView.findViewById(R.id.companiya)).setText(securities.company);
        ((TextView) convertView.findViewById(R.id.capital1)).setText(String.valueOf(securities.price));
        ((ImageView) convertView.findViewById(R.id.icons)).setImageBitmap(BitmapFactory.decodeByteArray(securities.idImage , 0, securities.idImage.length));
        return convertView;
    }
}


