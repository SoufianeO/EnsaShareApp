package com.example.myapplication.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.DAO.PostDao;
import com.example.myapplication.R;
import com.example.myapplication.model.Post;
import com.example.myapplication.model.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class see_profil extends AppCompatActivity {
    private TextView lNamefName,userName;
    private CircleImageView imageStudent;
    private Student student;
    private List<Post> posts;
    private PostDao postDao;
    private RecyclerView postRecycleView;
    private MyAdapterPosts postAdapter;
    private String nameGroup;
    private Student currentStudent;
    private boolean isAdmin;



    public void init(){
        isAdmin  = false;
        lNamefName = (TextView)findViewById(R.id.fNmaMelName);
        userName = (TextView) findViewById(R.id.userName);
        imageStudent = (CircleImageView) findViewById(R.id.imageStudent);
        posts = new ArrayList<Post>();
        postRecycleView = (RecyclerView)findViewById(R.id.postsRecycleView);
        postRecycleView.setLayoutManager( new LinearLayoutManager(getApplicationContext()));
        postDao = new PostDao(nameGroup);
        postDao.getPostsByUserName(student);
        isAdmin(student.getUserName());

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_profil);
        Bundle extras = getIntent().getExtras();

        this.student = (Student) extras.getSerializable("student");
        this.nameGroup = extras.getString("group");
        this.currentStudent = (Student) extras.getSerializable("currentStudent");
        init();
        Picasso.get().load( student.getProfilePic()).into(imageStudent);
        lNamefName.setText(student.getFirstName() + " " + student.getLastName());
        userName.setText( "UserName : " +student.getUserName());
        load();

    }

    public void load(){
        postDao.getDatabaseReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                posts = postDao.getPosts();
                postAdapter = new MyAdapterPosts(getApplicationContext(),
                       postDao.getPosts(),currentStudent ,nameGroup);
                postRecycleView.setAdapter(postAdapter);
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
                    see_profil.this.isAdmin=true;
                    Toast.makeText(getApplicationContext(), Boolean.toString(isAdmin),
                            Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}
