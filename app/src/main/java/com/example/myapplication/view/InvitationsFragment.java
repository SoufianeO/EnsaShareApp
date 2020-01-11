package com.example.myapplication.view;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DAO.AdminDao;
import com.example.myapplication.R;
import com.example.myapplication.model.Admin;
import com.example.myapplication.model.Invitation;
import com.example.myapplication.model.Student;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class InvitationsFragment extends Fragment {
    DatabaseReference reference;
    RecyclerView recyclerView;
    AdminDao adminDao;
    ArrayList<Invitation> listInvitation;
    InvitationAdapter adapter;
    View view;
    Admin admin;
    Student student;

    public void init(){
        adminDao = new AdminDao(view.getContext(), student.getUserName());
        adminDao.getAdmin();
        reference = FirebaseDatabase.getInstance().getReference("invitations");

    }

    public InvitationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        this.view = inflater.inflate(R.layout.fragment_invitations, container, false);
        final Context context= getActivity().getApplicationContext();
        final FragmentActivity c = getActivity();
        admin_Profile activity = (admin_Profile) getActivity();
        this.student = activity.getMyData();
        init();

        recyclerView = (RecyclerView) view.findViewById(R.id.invitationsRecycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);




        Query query = reference.orderByChild("admin")
                .equalTo(student.getUserName());
        query.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listInvitation= new ArrayList<Invitation>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Invitation invitation = dataSnapshot1.getValue(Invitation.class);
                    listInvitation.add(invitation);
                }

               load();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






       /*
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listInvitation= new ArrayList<Invitation>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Invitation invitation = dataSnapshot1.getValue(Invitation.class);
                    listInvitation.add(invitation);
                }
                adapter = new InvitationAdapter(context,listInvitation);
                recyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(context, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });*/

        return this.view ;
    }

    public void load(){
        adminDao.getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                InvitationsFragment.this.admin =  InvitationsFragment.this.adminDao.getAdminDao();
                adapter = new InvitationAdapter(InvitationsFragment.this.getContext(),listInvitation,admin.getGroup());
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
