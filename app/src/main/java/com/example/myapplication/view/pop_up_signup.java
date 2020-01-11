package com.example.myapplication.view;

import android.graphics.Color;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.app.Dialog;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

public class pop_up_signup extends AppCompatActivity {
      static String  typeSignup;
      static Intent intent;

    public static void showPopUpsignup(final MainActivity mainActivity){
        final ImageView teacher;
        final ImageView student;
        final TextView labelStudent;
        final TextView labelTeacher;
        final ImageView nextSignUp;
        Dialog dialog = new Dialog(mainActivity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pop_up_signup);
        teacher = (ImageView) dialog.findViewById(R.id.imageTeacher);
        student = (ImageView) dialog.findViewById(R.id.imageStudent);
        labelStudent =  dialog.findViewById(R.id.labelStudent);
        labelTeacher =  dialog.findViewById(R.id.labelTeacher);
        nextSignUp = (ImageView) dialog.findViewById(R.id.nextsignup);
        teacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeSignup = "teacher";
                teacher.setImageResource(R.drawable.teacher);
                labelTeacher.setTextColor(Color.parseColor("#f95959"));
                labelStudent.setTextColor(Color.parseColor("#ACACAC"));
                student.setImageResource(R.drawable.student1_nonclicked);
                nextSignUp.setVisibility(View.VISIBLE);




            }
        });
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeSignup = "student";
                student.setImageResource(R.drawable.student);
                labelStudent.setTextColor(Color.parseColor("#f95959"));
                labelTeacher.setTextColor(Color.parseColor("#ACACAC"));
                teacher.setImageResource(R.drawable.teachernonclicked);
                nextSignUp.setVisibility(View.VISIBLE);


            }
        });

        nextSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (typeSignup.contains("student")) {
                    intent = new Intent(mainActivity, signup1.class);
                    mainActivity.startActivity(intent);
                }
                else{
                    intent = new Intent(mainActivity,signup1teacher.class);
                    mainActivity.startActivity(intent);
                }


            }
        });


        dialog.show();

    }



}
