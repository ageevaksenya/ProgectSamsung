package com.example.myapplication111;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.util.Base64;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainRegistration extends AppCompatActivity {
    private ImageButton btnSend;
    private EditText etName;
    private EditText etEmail;
    private EditText Password;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainChoice.flag_registr = true;
        setContentView(R.layout.activity_main);
        btnSend = findViewById(R.id.btn_registr);
        etName = findViewById(R.id.nameusers);
        Password = findViewById(R.id.editTextTextPassword);
        etEmail = findViewById(R.id.editTextTextEmailAddress);
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.increase);
        btnSend.setOnClickListener((v) -> {
           v.startAnimation(animAlpha);
            String type ="registration";

            final Animation animation = AnimationUtils.loadAnimation(this, R.anim.earthquake);
            final View linear =  findViewById(R.id.linear);
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inSampleSize = 2;  //you can also calculate your inSampleSize
            options.inJustDecodeBounds = false;
            options.inTempStorage = new byte[16 * 1024];
//            Drawable  drawable= getResources().getDrawable(R.drawable.personimage_round, "@style/Theme.MyApplication111");
//
//            drawableToBitmap(drawable);
            final Bitmap bitmap =BitmapFactory.decodeResource(getResources(), R.drawable.personimage_round); ;
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);byte[] byteArray = stream.toByteArray();
            bitmap.recycle();
            Intent intent = new Intent(MainRegistration.this, MainChoice.class);
            MainRegistration.this.startActivity(intent);


//            Call<TypeReponse> call = MyServer.service.fetchUser(type,etName.getText().toString(),etEmail.getText().toString(),Password.getText().toString(),byteArray);
//            call.enqueue(new Callback<TypeReponse>() {
//
//
//                @Override
//                public void onResponse(Call<TypeReponse> call, Response<TypeReponse> response) {
//                    if (response.isSuccessful()) {
//                        if(!response.body().type.equals("Success")){
//                            linear.startAnimation(animation);
//                            Toast.makeText(MainRegistration.this, response.body().type, Toast.LENGTH_SHORT).show();
//                        }else {
//                            MyData.my_name = etName.getText().toString();
//                            MyData.my_image=byteArray;
//                            Toast.makeText(MainRegistration.this, "Вы успешно зарегестрированы!", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(MainRegistration.this, MainChoice.class);
//                            MainRegistration.this.startActivity(intent);
//                        }
//                    }else {
//                        Toast.makeText(MainRegistration.this, response.body().toString(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<TypeReponse> call, Throwable t) {
//                    if (t instanceof IOException) {
//                        Toast.makeText(MainRegistration.this, "сбой сети :( повторите попытку", Toast.LENGTH_SHORT).show();
//                    }
//                    else {
//                        Toast.makeText(MainRegistration.this, "проблема с конверсией! :(", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//            });
        });
    }
    public static Bitmap drawableToBitmap(Drawable drawable) {

        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable)drawable).getBitmap();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }
}