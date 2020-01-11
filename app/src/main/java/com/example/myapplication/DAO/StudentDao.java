package com.example.myapplication.DAO;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication.firebaseHelper.NodeCreator;
import com.example.myapplication.model.Post;
import com.example.myapplication.model.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class StudentDao {
    private DatabaseReference databaseReference ;
    public static Student student ;
    public static   HashMap<String, Object> groups ;
    public StudentDao() {
        NodeCreator nodeCreator = new NodeCreator("students");
        this.databaseReference=nodeCreator.getDatabaseReference();
    }

    public void registerStudent(Student s){


         databaseReference.child(s.getUserName()).setValue(s);

    }

    public static HashMap<String, Object> getGroups() {
        return groups;
    }

    public void getStudentByUsenrName(String username , final Context cx){

        databaseReference= FirebaseDatabase.getInstance().getReference("students");
        Query query = databaseReference.orderByChild("userName")
                .equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.exists()) {


                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                      StudentDao.student = snapshot.getValue(Student.class);
                        Object data = (HashMap<String, Object>) snapshot.child("groups").getValue();
                       StudentDao.groups = (HashMap<String, Object>) data;


                    }



                }
                else{
                    Toast.makeText(cx,"tel student n existe pas ",
                            Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    public static Student getStudent() {
        return student;
    }
}
