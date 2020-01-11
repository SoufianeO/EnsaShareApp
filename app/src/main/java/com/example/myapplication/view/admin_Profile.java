package com.example.myapplication.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;
import com.example.myapplication.model.Student;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class admin_Profile extends AppCompatActivity {
    // properties
    private BottomNavigationView mainNav ;
    private FrameLayout mainFrame ;
    private Fragment homeFragment ;
    private Fragment invitationFragment ;
    private Fragment notificationFragment ;
    private Fragment mapFragment ;
    private Student student ;



    public void init(){
        mainNav =(BottomNavigationView)findViewById(R.id.main_nav);
        mainFrame =(FrameLayout)findViewById(R.id.main_frame);
        homeFragment = new HomeFragment();
        invitationFragment = new InvitationsFragment();
        notificationFragment= new NotificationFragment();
        mapFragment=new MapFragment();
        setFramgment(homeFragment);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin__profile);
        init();
        Bundle extras = getIntent().getExtras();
        student = (Student) extras.getSerializable("student");


        //events
        mainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_home :
                    setFramgment(homeFragment);
                      return true ;

                      case R.id.nav_invitation :
                         setFramgment(invitationFragment);
                        return true ;

                    case R.id.nav_notification:
                        setFramgment(notificationFragment);
                        return true;

                        case R.id.nav_map:
                        setFramgment(mapFragment);
                        return true;


                   default:
                          return false ;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

    }

    public void setFramgment(Fragment framgment){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,framgment);
        fragmentTransaction.commit();
    }
    public Student getMyData() {
        return student;
    }
}
