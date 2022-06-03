package com.example.myapplication111;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPersonalInformation extends AppCompatActivity {
    private ImageView image;
    private static final int REQUEST = 1;
    SeekBar volumeControl;
    AudioManager audioManager;
    MediaPlayer player;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        ImageButton buttonBack=findViewById(R.id.back);


        player = MediaPlayer.create(this, R.raw.music);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curValue = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        volumeControl = findViewById(R.id.volumeControl);
        volumeControl.setMax(maxVolume);
        volumeControl.setProgress(curValue);
        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        image=findViewById(R.id.imageView8);
        image.setImageBitmap(BitmapFactory.decodeByteArray(MyData.my_image, 0, MyData.my_image.length));
        TextView wallet= findViewById(R.id.textView3);

        TextView Name= findViewById(R.id.nameus);
        Name.setText(MyData.my_name);
        ImageButton button=findViewById(R.id.imageButton9);
        Button button2 = findViewById(R.id.btn_filt);
        wallet.setText(MyData.getMy_purse());
        Name.setText(MyData.getMy_name());


        button2.setOnClickListener((View.OnClickListener) this);
        button.setOnClickListener(view -> {
            Intent intent = new Intent(MainPersonalInformation.this, MainPersonalInformation.class);
            MainPersonalInformation.this.startActivity(intent);


        });
        button2.setOnClickListener(view -> {
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, REQUEST);
        });
        buttonBack.setOnClickListener(view -> finish());

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        MenuItem shareMenuItem3 = menu.findItem(R.id.menu_search);
        shareMenuItem3.setVisible(false);
        MenuItem shareMenuItem2 = menu.findItem(R.id.menusound);
        shareMenuItem2.setVisible(false);
        return true;
    }

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
                        Toast.makeText(MainPersonalInformation.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Tk> call, Throwable t) {
                        if (t instanceof IOException) {
                            Toast.makeText(MainPersonalInformation.this, "сбой сети :( повторите попытку", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(MainPersonalInformation.this, "проблема с конверсией! :(", Toast.LENGTH_SHORT).show();
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
                item.setChecked(true);
                Intent intent=new Intent(this, MainAuthorization.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case REQUEST:
                if(resultCode == RESULT_OK){
                    try {
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        image.setImageBitmap(selectedImage);
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        selectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        selectedImage.recycle();
                        String type ="Photo";
                        Call<Tk> call = MyServer.service.photo(type,MyData.getToken(),byteArray);
                        call.enqueue(new Callback<Tk>() {
                            @Override
                            public void onResponse(Call<Tk> call, Response<Tk> response) {
                                if (response.isSuccessful()) {
                                   MyData myData=new MyData();
                                   myData.setToken(response.body().getToken());

                                }else {
                                    Toast.makeText(MainPersonalInformation.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<Tk> call, Throwable t) {
                                if (t instanceof IOException) {
                                    Toast.makeText(MainPersonalInformation.this, "сбой сети :( повторите попытку", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(MainPersonalInformation.this, "проблема с конверсией! :(", Toast.LENGTH_SHORT).show();
                                }
                            }


                        });
                    }catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(MainPersonalInformation.this, "Извините! Картинку установить не выйдет!:(", Toast.LENGTH_SHORT).show();
                }
        }}

}


