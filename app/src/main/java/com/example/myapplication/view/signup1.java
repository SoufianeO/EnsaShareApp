package com.example.myapplication.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.model.Student;

import de.hdodenhof.circleimageview.CircleImageView;

public class signup1 extends AppCompatActivity{
    private static final  int PICK_IMAGE_REQUEST = 1;
    private EditText  firstName ;
    private EditText  lastName ;
    private ImageView imagenext;
    private ImageView imageback;
    private CircleImageView profilePic ;
    private ImageView addImage ;
    private Intent i;
    private Uri imageUri ;
    private Spinner levelSpinner;
    private Student student;



    public void init(){
        profilePic = findViewById(R.id.imageGroup);

        addImage = findViewById(R.id.addImage);
        firstName = findViewById(R.id.groupname);
        lastName = findViewById(R.id.lastName);
        imagenext = findViewById(R.id.nextsignup2Image);
        imageback = findViewById(R.id.backmainImage);

        levelSpinner = findViewById(R.id.spinner);
        student = new Student();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup1);
        init();


        // add Image de profile event
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();

            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.levels_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        levelSpinner.setAdapter(adapter);


        //spinner event
        levelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                student.setLevel(parent.getItemAtPosition(position).toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        imagenext.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                student.setFirstName(firstName.getText().toString());
                student.setLastName(lastName.getText().toString());

                i = new Intent(signup1.this, signupstudent2.class);

                i.putExtra("student",student);

                i.putExtra("profileImage",imageUri);

                startActivity(i);






            }


        });


        imageback.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });

    }

    private void openFileChooser(){
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i ,PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null &&
                data.getData() != null){
            imageUri =data.getData();
            profilePic.setImageURI(imageUri);


        }

    }

}