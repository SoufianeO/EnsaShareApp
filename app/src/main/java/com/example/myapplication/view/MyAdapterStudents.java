package com.example.myapplication.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DAO.GroupDao;
import com.example.myapplication.R;
import com.example.myapplication.model.Student;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapterStudents extends RecyclerView.Adapter<MyAdapterStudents.MyViewHolder> {
    private Context context;
    List<Student> students;
    String group;
    Student student;
    boolean isAdmin;
    GroupDao groupDao;

    public MyAdapterStudents(String group,Context context , List<Student> students , Student student, boolean isAdmin){
        this.students = students;
        this.context = context;
        this.group = group;
        this.student = student;
        this.isAdmin = isAdmin;
        this.groupDao = new GroupDao(context,group.toLowerCase());

    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.studentscardview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.userName.setText(students.get(position).getFirstName() + " " + students.get(position).getLastName());
        Picasso.get().load(students.get(position).getProfilePic()).into(holder.profilePic);
        Toast.makeText(context, "" + isAdmin, Toast.LENGTH_SHORT).show();
        holder.buttonPosts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, see_profil.class);
                i.putExtra("student", students.get(position));
                i.putExtra("group",group);
                i.putExtra("currentStudent", student);
                context.startActivity(i);
            }
        });
        if(isAdmin==true) {
            holder.textViewOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(context, holder.textViewOptions);
                    popup.inflate(R.menu.postmenudelete);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.delete:
                                    groupDao.deleteStudent(students.get(position));
                                    students.clear();

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
            holder.textViewOptions.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView userName,textViewOptions;
        CircleImageView profilePic;
        Button buttonPosts;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.userName);
            profilePic = (CircleImageView) itemView.findViewById(R.id.profilePicUrl);
            buttonPosts = (Button) itemView.findViewById(R.id.buttonPosts);
            textViewOptions = (TextView) itemView.findViewById(R.id.textViewOptions);
        }
    }



}
