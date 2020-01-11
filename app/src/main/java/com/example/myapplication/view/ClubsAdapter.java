package com.example.myapplication.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DAO.InvitationDao;
import com.example.myapplication.R;
import com.example.myapplication.model.Group;
import com.example.myapplication.model.Invitation;
import com.example.myapplication.model.Student;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClubsAdapter extends RecyclerView.Adapter<ClubsAdapter.MyViewHolder>  {
    Context context;
    ArrayList<Group> groups;
    ArrayList<String> invitations ;
    Student student;



    public ClubsAdapter(Context c, ArrayList<Group> g , ArrayList<String> i , Student s) {

        context = c;
        groups = g;
        invitations=i;
        student=s;

    }

    @NonNull
    @Override

    public ClubsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClubsAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.clubscardview, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        String clubname = groups.get(position).getName();

        holder.clubname.setText(clubname);

        Picasso.get().load(groups.get(position).getUrlImage()).into(holder.clubPic);
        String invitationId = student.getUserName()+"-"+clubname ;

        if (invitations.contains(invitationId)){

            holder.request.setEnabled(false);
            holder.request.setBackgroundColor(Color.parseColor("#FFCD41"));
            holder.request.setText("Pending");

        }

        holder.onRequestClick(holder,context,position);

    }


    @Override
    public int getItemCount() {
        return groups.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView clubname;
        CircleImageView clubPic;
        Button request ;


        public MyViewHolder(View itemView) {
            super(itemView);


            clubname = (TextView) itemView.findViewById(R.id.clubname);
            clubPic = (CircleImageView) itemView.findViewById(R.id.imageClub);

            request = (Button) itemView.findViewById(R.id.request);


        }
        public void onRequestClick(@NonNull final MyViewHolder holder,final Context context , final int position ) {
            request.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {

                    String invitationId = student.getUserName()+"-"+groups.get(position).getName();

                    Invitation invitation = new Invitation(invitationId,student.getUserName(),student.getFirstName(),student.getLastName(),

                    student.getProfilePic(),groups.get(position).getAdmin().getUserName());
                    InvitationDao invitationDao= new InvitationDao();
                    invitationDao.stockInvitation(invitation);
                    holder.request.setActivated(false);
                    holder.request.setBackgroundColor(Color.parseColor("#FFCD41"));
                    holder.request.setText("Pending");

                    Toast.makeText(context, invitation.toString(), Toast.LENGTH_LONG).show();

                }
            });
        }







    }
}
