package com.example.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.DAO.StudentDao;
import com.example.myapplication.R;
import com.example.myapplication.model.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    // Buttons **********************
    private TextView usernmae ;
    private TextView password ;
    private Button signIn;
    private Button signup1;
    private ProgressBar loginProgress ;
    private FirebaseAuth userAuth ;
    private Intent adminProfile;
    private Intent studentProfile;
    public static  boolean isStudent =false;
    public static  boolean isAdmin =false;
    private StudentDao studentDao ;

    private void init(){

        usernmae = (TextView)findViewById(R.id.groupname);
        password=(TextView)findViewById(R.id.password);
        signIn=(Button) findViewById(R.id.signIn);
        loginProgress =(ProgressBar)findViewById(R.id.loginProgress);
        loginProgress.setVisibility(View.INVISIBLE);
        signup1 = (Button) findViewById(R.id.signUp);
        userAuth=FirebaseAuth.getInstance();
        adminProfile=new Intent(getApplicationContext() ,TestActivity.class);

        studentDao =new StudentDao();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        //BUTTONS EVENTS *********************
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = usernmae.getText().toString()+"@ensashare.com";
                String pass = password.getText().toString();
                if (email.isEmpty()||pass.isEmpty()){

                }else{
                    signIn(email,pass);
                }
            }
        });
        signup1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                pop_up_signup.showPopUpsignup(MainActivity.this);

            }

        });

    }
    public void signIn(String email , String pass){

        userAuth.signInWithEmailAndPassword(email , pass).addOnCompleteListener(
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            loginProgress.setVisibility(View.VISIBLE);
                            signIn.setActivated(false);
                            final FirebaseUser user = userAuth.getCurrentUser();
                            isStudent(user.getDisplayName());


                            Handler handler = new Handler();


                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {



                                    if (isStudent){


                                        studentDao.getStudentByUsenrName(user.getDisplayName(),getApplicationContext());
                                        Handler handler = new Handler();


                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {


                                                final Student student =StudentDao.getStudent();
                                                HashMap<String, Object> groups =StudentDao.getGroups();

                                                student.setGroups(groups);

                                                if (groups.containsValue(student.getLevel())){


                                                    isAdmin(user.getDisplayName());

                                                    Handler handler2 = new Handler();
                                                    handler2.postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {

                                                            if (isAdmin) {


                                                                adminProfile = new Intent(getApplicationContext(),admin_Profile.class);
                                                                adminProfile.putExtra("student",student);

                                                                startActivity(adminProfile);
                                                            }
                                                            else {



                                                                Intent studentProfile = new Intent(getApplicationContext(),student_profile.class);

                                                                studentProfile.putExtra("student",student);

                                                                startActivity(studentProfile);


                                                            }
                                                        }
                                                    }, 3000) ;
                                                }
                                                else {


                                                    loginProgress.setVisibility(View.INVISIBLE);
                                                    pop_up_notverified.showPopUpnotVerified(MainActivity.this);
                                                }
                                            }
                                        }, 5000) ;




                                    }

                                }
                            }, 8000) ;





                            //updateUI();

                        }else{


                        }

                    }
                });

    }

    public void updateUI(FirebaseUser user){


        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = userAuth.getCurrentUser();
        if (user!=null){

            // updateUI(user);


        }
    }

    public void isStudent(String username){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("students");
        Query query = databaseReference.orderByChild("userName")
                .equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    MainActivity.isStudent=true;


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    public void isAdmin(String username){

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("admins");
        Query query = databaseReference.orderByChild("userName")
                .equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    MainActivity.isAdmin=true;


                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}