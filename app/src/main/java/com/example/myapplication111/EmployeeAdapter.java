package com.example.myapplication111;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

class UsersB{
    String name="";
    int price=0;
    byte[] idImage;
    int id=0;


}

public class EmployeeAdapter extends PagedListAdapter<UsersB, EmployeeAdapter.UsersViewHolder> {

    private Context mCtx;

    EmployeeAdapter(Context mCtx) {
        super(DIFF_CALLBACK);
        this.mCtx = mCtx;
    }
    private static DiffUtil.ItemCallback<UsersB> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<UsersB>() {
                @Override
                public boolean areItemsTheSame(UsersB oldItem, UsersB newItem) {
                    return oldItem.id == newItem.id;
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull UsersB oldItem, @NonNull UsersB newItem) {
                    return oldItem.equals(newItem);
                }


            };
    static class UsersViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewPrice;
        ImageView iconImageView;
        TextView pos;
        ImageView iconImageViewpos;
        public void bind(UsersB users,int id) {
            if (users == null) {
                textViewName.setText("Ожидание");
                textViewPrice.setText("Ожидание");
            } else {
                if(id==1) iconImageView.setImageResource(R.mipmap.mesto_1_foreground);
                else if(id==2) iconImageView.setImageResource(R.mipmap.mesto_2_foreground);
                else if(id==3) iconImageView.setImageResource(R.mipmap.mesto_3_foreground);
                pos.setText(id);
                textViewName.setText(users.name);
                textViewPrice.setText(users.price);
                iconImageView.setImageBitmap(BitmapFactory.decodeByteArray(users.idImage , 0, users.idImage.length));
            }
        }
        public UsersViewHolder(View view){
            super(view);
            pos=view.findViewById(R.id.order);
            textViewName=view.findViewById(R.id.name);
            textViewPrice=view.findViewById(R.id.pr);
            iconImageView=view.findViewById(R.id.ic);
            iconImageViewpos = view.findViewById(R.id.im_orger);

        }


    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {
        if(Objects.requireNonNull(getItem(position)).name.equals(MyData.my_name)&& Objects.requireNonNull(getItem(position)).price==MyData.getMy_purse()){
                return 2;
        }
        return 0;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0: view = LayoutInflater.from(parent.getContext()).inflate(R.layout.runitem_layout, parent, false);
                  break;
            case 2: view = LayoutInflater.from(parent.getContext()).inflate(R.layout.runitem2, parent, false);;
                  break;
            default:
                throw new IllegalStateException("Unexpected value: " + viewType);
        }
        UsersViewHolder holder = new UsersViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        holder.bind(getItem(position), (int)getItemId(position)+1);
    }




}