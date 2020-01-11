package com.example.myapplication.DAO;

import com.example.myapplication.firebaseHelper.NodeCreator;
import com.example.myapplication.model.Teacher;
import com.google.firebase.database.DatabaseReference;

public class TeacherDao {
    private DatabaseReference databaseReference ;
    public TeacherDao(){
        NodeCreator nodeCreator = new NodeCreator("teachers");
        this.databaseReference=nodeCreator.getDatabaseReference();
    }
    public void registerTeacher(Teacher t ){
        String id = databaseReference.push().getKey();
        t.setTeacherId(id);
        databaseReference.child(id).setValue(t);


    }
}
