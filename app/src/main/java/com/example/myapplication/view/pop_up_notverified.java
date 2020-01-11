package com.example.myapplication.view;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class pop_up_notverified extends AppCompatActivity {

      static Intent intent;

    public static void showPopUpnotVerified(final MainActivity activity){


        final ImageView nextSignUp;

        Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pop_up_notverified);

        nextSignUp = (ImageView) dialog.findViewById(R.id.nextsignup);

        nextSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            intent = new Intent(activity ,MainActivity.class);

            activity.startActivity(intent);



            }
        });


        dialog.show();


    }



}
