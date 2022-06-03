package com.example.myapplication111;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainChoice extends AppCompatActivity {
    static public boolean flag_registr;
    MediaPlayer mPlayer;
    MediaPlayer player;
    static boolean flag_sound=true;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        player = MediaPlayer.create(this, R.raw.music);
        startService(new Intent(this, MyService.class));
        ImageButton buttonac =  findViewById(R.id.button20);
        ImageButton button2 = findViewById(R.id.button23);
        ImageButton button3 =  findViewById(R.id.button13);
        ImageButton button4 =  findViewById(R.id.button14);
        ImageButton button5 =  findViewById(R.id.im_port);
        ImageButton button6 =  findViewById(R.id.imageButtonPersonne);
        ImageButton button7 =  findViewById(R.id.imageButtonRun);
        ImageView im_robot=findViewById(R.id.im_robot);
        if(flag_registr ){
            im_robot.setVisibility(View.VISIBLE);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            im_robot.setVisibility(View.INVISIBLE);


        }


        buttonac.setOnClickListener(view -> {
            mPlayer=MediaPlayer.create(this, R.raw.cry);
            Intent intent = new Intent(MainChoice.this, MainListSecurities.class);
            intent.putExtra("textbutton", "АКЦИИ");
            MainChoice.this.startActivity(intent);



        });
        button2.setOnClickListener(view -> {
            mPlayer=MediaPlayer.create(this, R.raw.cry);
            Intent intent = new Intent(MainChoice.this, MainListSecurities.class);
            intent.putExtra("textbutton", "ОБЛИГАЦИИ");
            MainChoice.this.startActivity(intent);


        });
        button3.setOnClickListener(view -> {
            mPlayer=MediaPlayer.create(this, R.raw.cry);
            Intent intent = new Intent(MainChoice.this, MainListSecurities.class);
            intent.putExtra("textbutton", "ВАЛЮТА");
            MainChoice.this.startActivity(intent);


        });
        button4.setOnClickListener(view -> {
            mPlayer=MediaPlayer.create(this, R.raw.cry);
            Intent intent = new Intent(MainChoice.this, MainListSecurities.class);
            intent.putExtra("textbutton", "ФЬЮЧЕРСЫ");
            MainChoice.this.startActivity(intent);

        });
        button6.setOnClickListener(view -> {
            Intent intent = new Intent(MainChoice.this, MainPersonalInformation.class);
            MainChoice.this.startActivity(intent);

        });
        button7.setOnClickListener(view -> {
            Intent intent = new Intent(MainChoice.this, MainLeaders.class);
            MainChoice.this.startActivity(intent);

        });
        button5.setOnClickListener(view -> {
            Intent intent = new Intent(MainChoice.this, MainMyPurchase.class);
            MainChoice.this.startActivity(intent);
        });

    }

    Menu optionMenu;
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem shareMenuItem3 = menu.findItem(R.id.menu_search);
        shareMenuItem3.setVisible(false);
        MenuItem shareMenuItem2 = menu.findItem(R.id.menusound);
        shareMenuItem2.setVisible(false);
        optionMenu=menu;
        return true;
    }


    @SuppressLint("NonConstantResourceId")
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menutoken:
                item.setChecked(true);
                Call<Tk> call = MyServer.service.token("Token");
                call.enqueue(new Callback<Tk>() {
                    @Override
                    public void onResponse(Call<Tk> call, Response<Tk> response) {
                        if(response.isSuccessful()){
                            MyData myData=new MyData();
                            myData.setToken(response.body().getToken());

                        }
                        Toast.makeText(MainChoice.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Tk> call, Throwable t) {
                        if (t instanceof IOException) {
                            Toast.makeText(MainChoice.this, "сбой сети :( повторите попытку", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainChoice.this, "проблема с конверсией! :(", Toast.LENGTH_SHORT).show();
                        }
                    }

                });
                return true;
            case R.id.menusoundturn:
                item.setChecked(true);
                if( MainChoice.flag_sound){
                    stopService(new Intent(this, MyService.class));
                    item.setTitle("включить музыку");
                    MainChoice.flag_sound=false;
                }else{

                        startService(new Intent(this, MyService.class));
                        item.setTitle("выключить музыку");
                        MainChoice.flag_sound=true;
                    }


                return true;

            case R.id.menuoutput:
                stopService(new Intent(this, MyService.class));
                item.setChecked(true);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void onBackPressed(){
        stopService(new Intent(this, MyService.class));
        flag_sound=true;
        finish();


    }


}