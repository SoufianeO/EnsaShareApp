package com.example.myapplication.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.model.Invitation;
import com.example.myapplication.model.Student;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class InvitationAdapter extends RecyclerView.Adapter<InvitationAdapter.MyViewHolder> {


    Context context;
    ArrayList<Invitation> invitations;
    String level ;


    public InvitationAdapter(Context c, ArrayList<Invitation> i,String levele) {
        context = c;
        invitations = i;
        level=levele;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cardview, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String username = invitations.get(position).getFirstName()+" " + invitations.get(position).getLastName();
        holder.username.setText(username);

        Picasso.get().load(invitations.get(position).getProfilePic()).into(holder.profilePic);



        holder.onAccepterClick(invitations.get(position),context,position);

        holder.onRefuserClick(position,invitations.get(position));

    }

    @Override
    public int getItemCount() {
        return invitations.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        CircleImageView profilePic;
        Button accepter , refuser;


        public MyViewHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.username);
            profilePic = (CircleImageView) itemView.findViewById(R.id.imageProfile);
            accepter = (Button) itemView.findViewById(R.id.accepter);
            refuser = (Button) itemView.findViewById(R.id.refuser);
        }

        public void onAccepterClick(final Invitation invitation, final Context context , final int index) {
            accepter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String[]isGroup=invitation.getInvitationId().split("-");

                    if(!isGroup[1].startsWith("C")) {

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("students").child(invitation.getUserName()).child("groups");

                        Map<String, Object> taskMap = new HashMap<>();
                        taskMap.put(level, level);


                        databaseReference.setValue(taskMap, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                                        String groupName = level.toLowerCase();
                                        DatabaseReference Reference = FirebaseDatabase.getInstance().getReference("groups").child("group:" + groupName).child("students").child(invitation.getUserName());
                                        Student s = new Student();
                                        s.setUserName(invitation.getUserName());
                                        s.setFirstName(invitation.getFirstName());
                                        s.setLastName(invitation.getLastName());
                                        s.setProfilePic(invitation.getProfilePic());
                                        s.setLevel(level);
                                        Reference.setValue(s);
                                        deleteItem(index);
                                        Reference = FirebaseDatabase.getInstance().getReference("invitations").child(invitation.getInvitationId());


                                        Reference.removeValue();




                                    }

                                }

                        );

                    }else{
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("students").child(invitation.getUserName()).child("groups").child(level);

                       /* Map<String, Object> taskMap = new HashMap<>();
                        taskMap.put(level, level);*/


                        databaseReference.setValue(level, new DatabaseReference.CompletionListener() {
                                    @Override
                                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                                        String clubName = level.toLowerCase();
                                        DatabaseReference Reference = FirebaseDatabase.getInstance().getReference("clubs").child("club:" + clubName).child("students").child(invitation.getUserName());
                                        Student s = new Student();
                                        s.setUserName(invitation.getUserName());
                                        s.setFirstName(invitation.getFirstName());
                                        s.setLastName(invitation.getLastName());
                                        s.setProfilePic(invitation.getProfilePic());
                                        s.setLevel(level);
                                        Reference.setValue(s);
                                        deleteItem(index);
                                        Reference = FirebaseDatabase.getInstance().getReference("invitations").child(invitation.getInvitationId());

                                        Reference.removeValue();




                                    }

                                }

                        );

                    }


                }
            });
        }
        public void onRefuserClick(final int index, final Invitation invitation) {
            refuser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //deleteItem(index);
                    refuserInvitation(invitation ,context, index);
                }
            });
        }

        public   void deleteItem(int index) {
            invitations.remove(index);
            notifyItemRemoved(index);
        }

        public void refuserInvitation(final Invitation invitation, final Context context , final int index) {
            String[] isGroup = invitation.getInvitationId().split("-");

            if (!isGroup[1].startsWith("C")) {

                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("invitations").child(invitation.getInvitationId());
                databaseReference.removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                        DatabaseReference Reference = FirebaseDatabase.getInstance().getReference("students").child(invitation.getUserName());
                        Reference.removeValue(new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                                deleteItem(index);
                            }
                        });
                    }
                });

            }
            else {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("invitations").child(invitation.getInvitationId());

                databaseReference.removeValue(new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                        deleteItem(index);
                    }
                });

            }
        }
    }
}