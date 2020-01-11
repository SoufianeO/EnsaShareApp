package com.example.myapplication.DAO;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.myapplication.model.Admin;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AdminDao {
    private DatabaseReference ref;
    private Context context;
    private Admin adminDao;

    public DatabaseReference getRef() {
        return ref;
    }

    public Context getContext() {
        return context;
    }

    public Admin getAdminDao() {
        return adminDao;
    }



    public AdminDao(final Context context, String username) {
        ref = FirebaseDatabase.getInstance().getReference().child("admins").child(username);
        this.context = context;
    }


    public void getAdmin(){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap<String, Object> mapAdmin = (HashMap<String, Object>) dataSnapshot.getValue();
                AdminDao.this.adminDao = new Admin( (String) mapAdmin.get("userName"), (String) mapAdmin.get("group"));
              //  Toast.makeText(context, adminDao.getGroup(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
