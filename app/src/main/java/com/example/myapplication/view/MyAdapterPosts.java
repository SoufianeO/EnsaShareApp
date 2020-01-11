package com.example.myapplication.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DAO.PostDao;
import com.example.myapplication.R;
import com.example.myapplication.firebaseHelper.FileUploader;
import com.example.myapplication.model.Post;
import com.example.myapplication.model.Student;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static androidx.core.app.ActivityCompat.requestPermissions;

public class MyAdapterPosts extends RecyclerView.Adapter<MyAdapterPosts.MyViewHolder> {
    private Context context;
    List<Post> posts;
    Student student;
    PostDao postDao;
    FileUploader fileuploader;
    Activity activity;

    public MyAdapterPosts(Context context , List<Post> posts, Student student,String group, Activity activity){
        this.posts = posts;
        this.context = context;
        this.student = student;
        postDao = new PostDao(group);
        fileuploader = new FileUploader(context,"posts-files");
        this.activity = activity;
    }
    public MyAdapterPosts(Context context , List<Post> posts, Student student,String group){
        this.posts = posts;
        this.context = context;
        this.student = student;
        postDao = new PostDao(group);
        fileuploader = new FileUploader(context,"posts-files");
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.postscardview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        if (posts.get(position).getTypeFile().equals("text")) {
         holder.imagePost.setVisibility(View.GONE);
         holder.pdf.setVisibility(View.GONE);
            if (student.getUserName().equals(posts.get(position).getStudent().getUserName()))
            holder.textViewOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(context, holder.textViewOptions);
                    popup.inflate(R.menu.postmenudelete);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch(item.getItemId()){
                                case R.id.delete:
                                    postDao.deletePost(posts.get(position));
                                    posts.clear();

                                    break;

                            }
                            return false;
                        }
                    });
                    popup.show();

                }
            });
            else
                holder.textViewOptions.setVisibility(View.GONE);



        }
        else if( posts.get(position).getTypeFile().equals("pdf")){
            holder.imagePost.setVisibility(View.GONE);
            if (student.getUserName().equals(posts.get(position).getStudent().getUserName()))
            holder.textViewOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(context, holder.textViewOptions);
                    popup.inflate(R.menu.postmenudeletedownload);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch(item.getItemId()){
                                case R.id.delete:
                                    postDao.deletePost(posts.get(position));
                                    fileuploader.deleteFile(posts.get(position).getUrlFile());
                                    posts.clear();


                                    break;
                                case R.id.download:
                                    fileuploader.downloadFile(posts.get(position).getUrlFile());

                                    break;

                            }
                            return false;
                        }
                    });
                    popup.show();

                }
            });
            else
            holder.textViewOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(context, holder.textViewOptions);
                    popup.inflate(R.menu.postmenudownload);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch(item.getItemId()){
                                case R.id.download:
                                    fileuploader.downloadFile(posts.get(position).getUrlFile());

                                    break;

                            }
                            return false;
                        }
                    });
                    popup.show();

                }
            });


        }
        else{

            holder.pdf.setVisibility(View.GONE);
            Picasso.get().load(posts.get(position).getUrlFile()).into(holder.imagePost);
            if (student.getUserName().equals(posts.get(position).getStudent().getUserName()))
                holder.textViewOptions.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popup = new PopupMenu(context, holder.textViewOptions);
                        popup.inflate(R.menu.postmenudeletedownload);
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch(item.getItemId()){
                                    case R.id.delete:
                                        postDao.deletePost(posts.get(position));
                                        fileuploader.deleteFile(posts.get(position).getUrlFile());
                                        posts.clear();

                                        break;
                                    case R.id.download:
                                        fileuploader.downloadFile(posts.get(position).getUrlFile());

                                        break;

                                }
                                return false;
                            }
                        });
                        popup.show();

                    }
                });
            else
                holder.textViewOptions.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popup = new PopupMenu(context, holder.textViewOptions);
                        popup.inflate(R.menu.postmenudownload);
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch(item.getItemId()){
                                    case R.id.download:
                                        fileuploader.downloadFile(posts.get(position).getUrlFile());


                                        break;

                                }
                                return false;
                            }
                        });
                        popup.show();

                    }
                });
        }

        if(this.student.getUserName().equals(posts.get(position).getStudent().getUserName())){

        }
        holder.date.setText(posts.get(position).getDate());
        holder.content.setText(posts.get(position).getContent());
        holder.userName.setText(posts.get(position).getStudent().getFirstName() + " " +
                posts.get(position).getStudent().getLastName());
        Picasso.get().load(posts.get(position).getStudent().getProfilePic()).into(holder.profilePicStudent);




    }



    @Override
    public int getItemCount() {
        return posts.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView userName, date, content;
        CircleImageView profilePicStudent;
        ImageView imagePost,pdf;
        TextView textViewOptions;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.userNamePost);
            date = (TextView) itemView.findViewById(R.id.datePost);
            content = (TextView) itemView.findViewById(R.id.contentPost);
            profilePicStudent = (CircleImageView) itemView.findViewById(R.id.ImageStudentPost);
            imagePost = (ImageView) itemView.findViewById(R.id.imagePost);
            pdf = (ImageView) itemView.findViewById(R.id.imagepdf);
            textViewOptions = (TextView) itemView.findViewById(R.id.textViewOptions);

        }
    }


}
