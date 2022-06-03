package com.example.myapplication111;

import android.annotation.SuppressLint;
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
class UsersA{
    String name="";
    int price=0;
    byte[] idImage;


}
public class UsersAdapter extends ArrayAdapter<UsersA> {
    public UsersAdapter(@NonNull Context context, List<UsersA> list) {
        super(context,  R.layout.runitem_layout, list);
    }
    public int getViewTypeCount() {
        return 2;
    }


    @SuppressLint("InflateParams")
    public View getView(int position, View convertView, ViewGroup parent) {

        final UsersA usersA = getItem(position);

        if (convertView == null && MyData.my_name.equals(usersA.name) && MyData.getMy_purse()==usersA.price) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.runitem2, null);
        }else if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.runitem_layout, null);
        }
        ImageView iconImageView = convertView .findViewById(R.id.im_orger);
        if (position==0) {
            iconImageView.setImageResource(R.mipmap.mesto_1_foreground);
        } else if(position==1){
            iconImageView.setImageResource(R.mipmap.mesto_2_foreground);
        }else if(position==2){
            iconImageView.setImageResource(R.mipmap.mesto_3_foreground);
        }


// Заполняем адаптер
        ((TextView) convertView.findViewById(R.id.order)).setText(position+1);
        ((TextView) convertView.findViewById(R.id.nam)).setText(usersA.name);
        ((TextView) convertView.findViewById(R.id.pr)).setText(String.valueOf(usersA.price));
        ((ImageView) convertView.findViewById(R.id.ic)).setImageBitmap(BitmapFactory.decodeByteArray(usersA.idImage , 0, usersA.idImage.length));
        return convertView;
    }


}


