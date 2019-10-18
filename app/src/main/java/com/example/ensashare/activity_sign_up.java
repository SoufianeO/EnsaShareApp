package com.example.ensashare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class activity_sign_up extends AppCompatActivity implements View.OnClickListener {

    Button b;
    TextView tv;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        tv = findViewById(R.id.editText);
        result = findViewById(R.id.editText);

        b.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){

        String s = tv.getText().toString();
        String ss = "Bonjour"+s;
        result.setText(ss);
    }
}
