package com.example.myapplication.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DAO.GroupDao;
import com.example.myapplication.DAO.PostDao;
import com.example.myapplication.R;
import com.example.myapplication.model.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class group_files extends Fragment {

    View view;
    List<Post> posts;
    MyAdapterFIles filesAdapter;
    RecyclerView filesRecycleView;
    PostDao postDao;
    group activity;


    public void init(){
        activity = (group) getActivity();
        posts = new ArrayList<Post>();
        postDao = new PostDao(activity.getNameGroup());
        filesRecycleView = (RecyclerView) this.view.findViewById(R.id.filesRecycleView);
        filesRecycleView.setLayoutManager(new LinearLayoutManager(this.view.getContext()));

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.view = inflater.inflate(R.layout.activity_group_files,container,false);
        init();
        load();
        return this.view;
    }



    public void load(){
        postDao.getFiles(this.view.getContext());

        postDao.getDatabaseReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                group_files.this.posts = postDao.getPosts();
                Toast.makeText(getContext(), " " + posts.size() , Toast.LENGTH_LONG).show();
                group_files.this.filesAdapter= new MyAdapterFIles(group_files.this.view.getContext(),
                        group_files.this.postDao.getPosts());
                group_files.this.filesRecycleView.setAdapter(filesAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
