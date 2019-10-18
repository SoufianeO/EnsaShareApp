package com.example.ensashare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {
    Button signIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signIn = (Button)findViewById(R.id.signIn);

        signIn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(MainActivity.this,activity_signIn.class);
                startActivity(i);
            }
        });

    }

    public void afficheDetail(View v){
        startActivity(new Intent(MainActivity.this,activity_sign_up.class));

    }

}

