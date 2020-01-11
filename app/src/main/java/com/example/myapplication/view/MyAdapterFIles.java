package com.example.myapplication.view;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DAO.PostDao;
import com.example.myapplication.R;
import com.example.myapplication.firebaseHelper.FileUploader;
import com.example.myapplication.model.Post;
import com.example.myapplication.model.Student;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapterFIles extends RecyclerView.Adapter<MyAdapterFIles.MyViewHolder> {
    private Context context;
    List<Post> posts;


    FileUploader fileuploader;


    public MyAdapterFIles(Context context, List<Post> posts) {
        this.posts = posts;
        this.context = context;

        fileuploader = new FileUploader(context, "posts-files");

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.filescardview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

            holder.textViewOptions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(context, holder.textViewOptions);
                    popup.inflate(R.menu.postmenudownload);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
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

        holder.nameFile.setText(posts.get(position).getContent());
        holder.date.setText(posts.get(position).getDate());

    }



    @Override
    public int getItemCount() {
        return posts.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nameFile, date;
        TextView textViewOptions;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameFile = (TextView) itemView.findViewById(R.id.nameFile);
            date = (TextView) itemView.findViewById(R.id.dateFile);
            textViewOptions = (TextView) itemView.findViewById(R.id.textViewOptions);

        }
    }


}
