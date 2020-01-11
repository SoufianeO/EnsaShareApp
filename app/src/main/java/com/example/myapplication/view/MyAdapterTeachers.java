package com.example.myapplication.view;

import android.content.Context;
import android.telecom.GatewayInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Teacher;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapterTeachers extends RecyclerView.Adapter<MyAdapterTeachers.MyViewHolder> {
    private Context context;
    List<Teacher> teachers;
    public MyAdapterTeachers(Context context , List<Teacher> teachers){
        this.teachers = teachers;
        this.context = context;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.studentscardview,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.userName.setText(teachers.get(position).getFirstName() + " " + teachers.get(position).getLastName());
        Picasso.get().load(teachers.get(position).getProfilePic()).into(holder.profilePic);

    }

    @Override
    public int getItemCount() {
        return teachers.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView userName, textViewOptions;
        CircleImageView profilePic;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = (TextView) itemView.findViewById(R.id.userName);
            profilePic = (CircleImageView) itemView.findViewById(R.id.profilePicUrl);
            textViewOptions = (TextView) itemView.findViewById(R.id.textViewOptions);
            textViewOptions.setVisibility(View.GONE);
        }
    }

}
