package com.example.myapplication111;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainAuthorization extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        MainChoice.flag_registr=false;
        TextView textView =  findViewById(R.id.textacc);
        textView.setPaintFlags(textView.getPaintFlags()|Paint.UNDERLINE_TEXT_FLAG);
        Button button1 = findViewById(R.id.btn_filt);
        EditText etEmail =  findViewById(R.id.editTextTextEmailAddress);
        EditText etPassword =  findViewById(R.id.editTextTextPassword);
        ImageButton button2 =  findViewById(R.id.btn_enter);
        final Animation animAlpha = AnimationUtils.loadAnimation(this, R.anim.increase);

        button1.setOnClickListener(view -> {
            Intent intent = new Intent(MainAuthorization.this, MainRegistration.class);
            MainAuthorization.this.startActivity(intent);
        });
        button2.setOnClickListener(view -> {
            view.startAnimation(animAlpha);
            String type="auntefication";
            String email1 = etEmail.getText().toString();
            String password1 = etPassword.getText().toString();
            Call<MyData> call = MyServer.service.AUser(type, email1, password1);
           call.enqueue(new Callback<MyData>() {
              @Override
               public void onResponse(Call<MyData> call, Response<MyData> response) {
                    if(response.isSuccessful()){
                        MyData.setMy_name(response.body().getMy_name());
                        assert response.body() != null;
                        MyData.setMy_image(response.body().getMy_image());
                        MyData.setMy_purse(response.body().getMy_purse());
                        MyData.setToken(response.body().getToken());
                        MyData.setId(response.body().getId());
                       Intent intent = new Intent(MainAuthorization.this, MainChoice.class);
                        MainAuthorization.this.startActivity(intent);
                    }
                    Toast.makeText(MainAuthorization.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<MyData> call, Throwable t) {
                   if (t instanceof IOException) {
                        Toast.makeText(MainAuthorization.this, "сбой сети :( повторите попытку", Toast.LENGTH_SHORT).show();
                  }
                    else {
                       Toast.makeText(MainAuthorization.this, "проблема с конверсией! :(", Toast.LENGTH_SHORT).show();         }
               }

           });


        });
    }
}