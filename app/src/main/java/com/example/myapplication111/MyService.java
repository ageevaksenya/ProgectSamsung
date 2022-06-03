package com.example.myapplication111;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;


import androidx.annotation.Nullable;

public class MyService extends Service {
    private static final String TAG = "MyService";
    MediaPlayer player;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        player = MediaPlayer.create(this, R.raw.music);
        player.setLooping(true);
    }

    @Override
    public void onDestroy() {
        player.stop();
    }


    public int onStartCommand(Intent intent, int flags, int startId) {
       player.start();

        return super.onStartCommand(intent, flags, startId);
    }



}
