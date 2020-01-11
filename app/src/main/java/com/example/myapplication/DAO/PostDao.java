package com.example.myapplication.DAO;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication.model.Group;
import com.example.myapplication.model.Post;
import com.example.myapplication.model.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class PostDao {
    private DatabaseReference databaseReference ;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private String group;

    public List<Post> getPosts() {
        return posts;
    }

    private List<Post> posts = new ArrayList<Post>();



    public PostDao(String group) {
        this.group = group;
        if (group.charAt(0) == 'g')
        databaseReference = database.getReference().child("groups").child("group:"+group.toLowerCase()).child("posts").orderByKey().getRef();
        else
            databaseReference = database.getReference().child("clubs").child("club:"+group.toLowerCase()).child("posts").orderByKey().getRef();
    }

    public void registerPost(Post post){
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String dateString = format.format(new Date());
        format = new SimpleDateFormat("yyyyMMddHHmmss");
        String key = format.format(new Date());
        post.setDate(dateString);
        databaseReference.child(key).setValue(post);
    }

    public DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

    public FirebaseDatabase getDatabase() {
        return database;
    }

    public void getListPosts(final Context context){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object postsObject = (Object) dataSnapshot.getValue();
                if (postsObject != null) {
                    HashMap<String, Object> postsMap = (HashMap<String, Object>) postsObject;
                    SortedSet<String> keys  = new TreeSet<>(postsMap.keySet()).descendingSet();
                    for (String key : keys) {

                        HashMap<String, Object> postObject = (HashMap<String, Object>) postsMap.get(key);
                        Post p = new Post();
                        p.setContent((String) postObject.get("content"));
                        p.setTypeFile((String) postObject.get("typeFile"));
                        p.setDate((String) postObject.get("date"));
                        p.setUrlFile((String) postObject.get("urlFile"));
                        p.setId(key);
                        Object studentObject = (Object) postObject.get("student");
                        HashMap<String, Object> studentData = (HashMap<String, Object>) studentObject;
                        Student student = new Student((String) studentData.get("firstName"), (String) studentData.get("lastName"), (String) studentData.get("userName"), (String) studentData.get("level"), (String) studentData.get("profilePic"));
                        p.setStudent(student);
                        posts.add(p);
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getFiles(final Context context){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object postsObject = (Object) dataSnapshot.getValue();
                if (postsObject != null) {
                    HashMap<String, Object> postsMap = (HashMap<String, Object>) postsObject;
                    SortedSet<String> keys  = new TreeSet<>(postsMap.keySet()).descendingSet();
                    for (String key : keys) {

                        HashMap<String, Object> postObject = (HashMap<String, Object>) postsMap.get(key);
                        Post p = new Post();
                        p.setContent((String) postObject.get("content"));
                        p.setTypeFile((String) postObject.get("typeFile"));
                        p.setDate((String) postObject.get("date"));
                        p.setUrlFile((String) postObject.get("urlFile"));
                        p.setId(key);
                        Object studentObject = (Object) postObject.get("student");
                        HashMap<String, Object> studentData = (HashMap<String, Object>) studentObject;
                        Student student = new Student((String) studentData.get("firstName"), (String) studentData.get("lastName"), (String) studentData.get("userName"), (String) studentData.get("level"), (String) studentData.get("profilePic"));
                        p.setStudent(student);
                        if (p.getTypeFile().equals("pdf")) {
                            posts.add(p);
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void getPostsByUserName(final Student s){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Object postsObject = (Object) dataSnapshot.getValue();
                if (postsObject != null) {
                    HashMap<String, Object> postsMap = (HashMap<String, Object>) postsObject;
                    SortedSet<String> keys  = new TreeSet<>(postsMap.keySet()).descendingSet();
                    for (String key : keys) {

                        HashMap<String, Object> postObject = (HashMap<String, Object>) postsMap.get(key);
                        Post p = new Post();
                        p.setContent((String) postObject.get("content"));
                        p.setTypeFile((String) postObject.get("typeFile"));
                        p.setDate((String) postObject.get("date"));
                        p.setUrlFile((String) postObject.get("urlFile"));
                        p.setId(key);
                        Object studentObject = (Object) postObject.get("student");
                        HashMap<String, Object> studentData = (HashMap<String, Object>) studentObject;

                        Student student = new Student((String) studentData.get("firstName"), (String) studentData.get("lastName"), (String) studentData.get("userName"), (String) studentData.get("level"), (String) studentData.get("profilePic"));
                        p.setStudent(student);
                        if (s.getUserName().equals(student.getUserName())) {
                            posts.add(p);
                        }
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void deletePost(Post post){
        databaseReference.child(post.getId()).getRef().removeValue();
    }
}
