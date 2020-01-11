package com.example.myapplication.DAO;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication.firebaseHelper.NodeCreator;
import com.example.myapplication.model.Invitation;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InvitationDao {
    private DatabaseReference databaseReference ;
    private DatabaseReference ref ;
    private ArrayList<String> invitations= new ArrayList<>();

    public InvitationDao() {
            NodeCreator nodeCreator = new NodeCreator("invitations");
            this.databaseReference=nodeCreator.getDatabaseReference();
    }

    public ArrayList<String> getInvitation() {
        return invitations;
    }

    public void stockInvitation(Invitation invitation){

        databaseReference.child(invitation.getInvitationId()).setValue(invitation);

    }

    public void getInvitaions( final Context context) {

        ref = FirebaseDatabase.getInstance().getReference().child("invitations");

        ref.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                   Invitation invitation = snapshot.getValue(Invitation.class);

                   invitations.add(invitation.getInvitationId());




                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }

        });


    }


}
