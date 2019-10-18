package com.example.ensashare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class activity_signIn extends AppCompatActivity {

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
    }

    @Override
    protected void onResume(){
        this.mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.fff);
        this.mediaPlayer.setLooping(true);
        this.mediaPlayer.start();
        super.onResume();
    }

    @Override
    protected void onPause(){
        this.mediaPlayer.stop();
        this.mediaPlayer.release();
        super.onPause();
    }

}
