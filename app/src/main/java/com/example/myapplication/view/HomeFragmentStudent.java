package com.example.myapplication.view;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.DAO.GroupDao;
import com.example.myapplication.DAO.InvitationDao;
import com.example.myapplication.DAO.QuoteDao;
import com.example.myapplication.R;
import com.example.myapplication.model.Group;
import com.example.myapplication.model.Quote;
import com.example.myapplication.model.Student;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragmentStudent extends Fragment {
private TextView nomComplet ;
private CircleImageView imageProfile ;
private GroupDao groupDao;
private Group group;
private FirebaseAuth userAuth ;


    private FirebaseUser currentUser ;
    RecyclerView recyclerView;
    RecyclerView clubRecyclerView;
    ArrayList<Group> listclub;
    ArrayList<String> listInvitations;
    ArrayList<Group> listGroup;
    GroupsAdapter adapter;
    private Quote quot ;
    private QuoteDao quoteDao ;
    View view;
    Context context ;
    TextView quote ;
    ClubsAdapter adapterClub ;
    private InvitationDao invitationDao ;
    Student student;

public void init(){

    userAuth = FirebaseAuth.getInstance();
    currentUser = userAuth.getCurrentUser();
    quoteDao = new QuoteDao(view.getContext());
    quoteDao.getQuote();
    listclub = new ArrayList<>() ;
    listInvitations= new ArrayList<>();
    invitationDao = new InvitationDao();
    listGroup = new ArrayList<>() ;
    groupDao = new GroupDao();

}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.view = inflater.inflate(R.layout.fragment_home, container, false);

        this.context= getActivity().getApplicationContext();
        quote = (TextView)view.findViewById(R.id.quote);
        nomComplet = (TextView) view.findViewById(R.id.nomComplet);
        imageProfile = (CircleImageView) view.findViewById(R.id.imageGroup);
        recyclerView = (RecyclerView) view.findViewById(R.id.groupRecycle);
        clubRecyclerView = (RecyclerView) view.findViewById(R.id.clubRecycle);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        LinearLayoutManager layoutManageri = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);

        clubRecyclerView.setLayoutManager(layoutManageri);


        init();
        load();

        student_profile activity = (student_profile) getActivity();
         student = activity.getMyData();
        String nomprenom =student.getFirstName()+" "+student.getLastName();

        nomComplet.setText(nomprenom);

      // Picasso.get().load(currentUser.getPhotoUrl()).into( imageProfile );

        Glide.with(context).load(student.getProfilePic()).into(imageProfile);


       // Toast.makeText(context,currentUser.getPhotoUrl().toString(),Toast.LENGTH_SHORT).show();

        HashMap<String,Object> groups = student.getGroups();

        List <Object> list =  Collections.list(Collections.enumeration(groups.values()));
        invitationDao.getInvitaions(context);
        listInvitations=invitationDao.getInvitation();


        groupDao.getGroups(list , context);
        groupDao.getClubs(list , context);





        loadGroups();
        loadClubs();


        return this.view;

    }

    public  void load(){
        quoteDao.getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                HomeFragmentStudent.this.quot=  HomeFragmentStudent.this.quoteDao.getQuoteDao();

                HomeFragmentStudent.this.quote.setText(quot.getQuote());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }
    public  void loadGroups(){

        groupDao.getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listGroup = groupDao.getGroups();
                adapter = new GroupsAdapter(view.getContext(), HomeFragmentStudent.this.listGroup, HomeFragmentStudent.this.student);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }
    public  void loadClubs(){

        groupDao.getRef().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listclub= groupDao.getClub();
                adapterClub = new ClubsAdapter(view.getContext(), HomeFragmentStudent.this.listclub ,
                        HomeFragmentStudent.this.listInvitations,  HomeFragmentStudent.this.student);
                clubRecyclerView.setAdapter(adapterClub);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


    }

}
