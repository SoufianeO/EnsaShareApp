package com.example.myapplication.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DAO.PostDao;
import com.example.myapplication.R;
import com.example.myapplication.firebaseHelper.FileUploader;
import com.example.myapplication.model.Post;
import com.example.myapplication.model.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class group_posts extends Fragment {

    private EditText postEditText;
    private PostDao postDao;
    private Post post;
    private Uri uri;
    private PostDao postDaoGet;
    private SimpleDateFormat dateFormat;
    private ImageView fileButton;
    private ImageView imageButton;
    private ImageView sendButton;
    private View view;
    private FileUploader fileUploader;
    private boolean file;
    private boolean image;
    private List<Post> posts;
    private RecyclerView postRecycleView;
    private MyAdapterPosts postAdapter;
    private group activity;


    public void init(){
          String profilePiUrl = "https://firebasestorage.googleapis.com/v0/b/fir-test-cbd3b.appspot.com/o/students-profile-images%2F1572696980623.jpg?alt=media&token=42593483-d53d-42c7-b9eb-11dc062085ae";

          activity = (group) getActivity();

          postDao = new PostDao(activity.getNameGroup());
          postDaoGet = new PostDao(activity.getNameGroup());
          file = false;
          image = false;
          fileButton = (ImageView) this.view.findViewById(R.id.fileButton);
          imageButton = (ImageView) this.view.findViewById(R.id.imageButton);
          sendButton = (ImageView) this.view.findViewById(R.id.imageSend);
          post = new Post();
          posts = new ArrayList<Post>();
          post.setTypeFile("text");
          post.setUrlFile("text");
          post.setStudent(activity.getStudent());
          postEditText = (EditText) this.view.findViewById(R.id.postEditText);
          fileUploader = new FileUploader( this.view.getContext(), "posts-files");
          postDaoGet.getListPosts(view.getContext());
          postRecycleView = (RecyclerView)  this.view.findViewById(R.id.postsRecycleView);
          postRecycleView.setLayoutManager( new LinearLayoutManager(this.view.getContext()));


    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.activity_group_posts, container, false);
        init();
        load();


        // EVENTS
        postEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    postEditText.setText("");
                }

            }
        });

       fileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(file==false) selectedFile();
               else{
                   fileButton.setImageResource(R.drawable.file);
                   file = false;
                   image = false;
                   group_posts.this.post.setTypeFile("text");
                   group_posts.this.post.setUrlFile("text");
               }
            }
        });

       imageButton.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v) {
               if(image==false) selectImage();
               else{
                   imageButton.setImageResource(R.drawable.image);
                   file = false;
                   image = false;
                   group_posts.this.post.setTypeFile("text");
                   group_posts.this.post.setUrlFile("text");
               }
           }
       });


       sendButton.setOnClickListener(new View.OnClickListener(){

           @Override
           public void onClick(View v) {

               post.setContent(postEditText.getText().toString());
               if (!post.getTypeFile().contains("text")){
                   fileUploader.uploadFile2(group_posts.this.uri);
                   fileUploader.getUrlTask().addOnCompleteListener(new OnCompleteListener<Uri>() {
                       @Override
                       public void onComplete(@NonNull final Task<Uri> task) {
                           task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                               @Override
                               public void onSuccess(Uri uri) {


                                   post.setUrlFile(fileUploader.getUrl());
                                   postDao.registerPost(post);
                                   postEditText.setText("Share your post");
                                   InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
                                   inputManager.hideSoftInputFromWindow(postEditText.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                                   postEditText.clearFocus();
                                   image = false;
                                   file = false;
                                   fileButton.setImageResource(R.drawable.file);
                                   imageButton.setImageResource(R.drawable.image);
                                   post = new Post();
                                   post.setTypeFile("text");
                                   post.setUrlFile("text");
                                   post.setStudent(activity.getStudent());
                                   postDaoGet.getPosts().clear();
                               }
                           });
                       }
                   });
               }
               else {
                   post.setUrlFile("text");
                   postDao.registerPost(post);
                   postEditText.setText("Share your post");
                   InputMethodManager inputManager = (InputMethodManager) getContext().getSystemService(getContext().INPUT_METHOD_SERVICE);
                   inputManager.hideSoftInputFromWindow(postEditText.getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
                   postEditText.clearFocus();
                   post = new Post();
                   post.setTypeFile("text");
                   post.setUrlFile("text");
                   post.setStudent(activity.getStudent());
                   postDaoGet.getPosts().clear();
               }

           }
       });
       return this.view;
    }


    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
        image = true;
        file = false;
    }

    private void selectedFile() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select PDP file"), 1);
        file = true;
        image = false;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null && file == true
        && image == false) {
            this.uri = data.getData();
            this.post.setTypeFile("pdf");
            fileButton.setImageResource(R.drawable.file_selected);
            imageButton.setImageResource(R.drawable.image);
        }
        else if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null && file == false
                && image == true){
            this.uri = data.getData();
            this.post.setTypeFile("image");
            imageButton.setImageResource(R.drawable.image_selected);
            fileButton.setImageResource(R.drawable.file);

        }
        else{
            fileButton.setImageResource(R.drawable.file);
            imageButton.setImageResource(R.drawable.image);
            image = false;
            file = false;
        }
    }

    public void load(){
        postDaoGet.getDatabaseReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                group_posts.this.posts = postDaoGet.getPosts();
                group_posts.this.postAdapter = new MyAdapterPosts(group_posts.this.view.getContext(),
                        group_posts.this.postDaoGet.getPosts(), group_posts.this.activity.getStudent(),activity.getNameGroup(), group_posts.this.getActivity());
                group_posts.this.postRecycleView.setAdapter(postAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
