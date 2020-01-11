package com.example.myapplication.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.DAO.StudentDao;
import com.example.myapplication.R;
import com.example.myapplication.firebaseHelper.FileUploader;
import com.example.myapplication.model.Invitation;
import com.example.myapplication.model.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class signupstudent2 extends AppCompatActivity {
    private ImageView imagebacksignup1;
    private Intent intent;
    private EditText username ;
    private EditText password ;
    private EditText passwordConfimed;
    private Button submit ;
    private StudentDao studentdao;
    FileUploader fileUploader ;
    private Student  student ;
    ProgressDialog progressDialog ;
    private FirebaseAuth studentAuth ;




    public void init(){
        username =(EditText) findViewById(R.id.groupname);
        password = (EditText) findViewById(R.id.password);
        passwordConfimed = (EditText) findViewById(R.id.passwordconfirmed);
        submit = (Button) findViewById(R.id.submit);
        studentdao  = new StudentDao() ;
        imagebacksignup1 = (ImageView) findViewById(R.id.backsignup1Image);
        fileUploader = new FileUploader(getApplicationContext(),
                "students-profile-images");
        progressDialog =new ProgressDialog(this);
        studentAuth= FirebaseAuth.getInstance();



    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupstudent2);
        init();

        Bundle extras = getIntent().getExtras();
        student = (Student) extras.getSerializable("student");
        final Uri imagePath = (Uri) extras.get("profileImage");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().equals(passwordConfimed.getText().toString())){

                    student.setUserName(username.getText().toString());
                    String email = student.getUserName()+"@ensaShare.com";
                    String pass = password.getText().toString();


                    studentAuth.createUserWithEmailAndPassword(email,pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()){

                                        fileUploader.uploadFile(imagePath ,progressDialog);

                                        Handler handler = new Handler();

                                        handler.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {

                                                student.setProfilePic(FileUploader.uri.toString());
                                                HashMap<String,Object> groups = new HashMap<>();
                                                groups.put("entry","entry");
                                                student.setGroups(groups);


                                                final String groupName = student.getLevel().toLowerCase();

                                                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference("groups").child("group:"+groupName);




                                                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                                        if (dataSnapshot.exists()) {


                                                            Object data = (HashMap<String, Object>) dataSnapshot.child("Admin").getValue();
                                                            HashMap<String, Object> dataMap = (HashMap<String, Object>) data;



                                                            String invitationid =student.getUserName()+"-"+groupName;


                                                            Invitation invitation = new Invitation(invitationid,student.getUserName(),student.getFirstName()
                                                                    , student.getLastName(), student.getProfilePic(), dataMap.get("userName").toString());





                                                            UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest
                                                                    .Builder()
                                                                    .setDisplayName(student.getUserName())
                                                                    .setPhotoUri(FileUploader.uri)
                                                                    .build();

                                                            studentAuth.getCurrentUser().updateProfile(profileUpdate);

                                                            student.sendInvitation(invitation);

                                                            studentdao.registerStudent(student);

                                                            pop_up_registered.showPopUpregistered(signupstudent2.this);

                                                        }

                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    }
                                                });


                                            }
                                        }, 7000);




                                    }else{

                                    }

                                }
                            });

                }else{


                }

            }
        });

        imagebacksignup1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), signup1.class);
                startActivity(intent);

            }
        });
    }



}


