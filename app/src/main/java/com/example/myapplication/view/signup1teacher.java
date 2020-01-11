package com.example.myapplication.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;
import com.example.myapplication.firebaseHelper.FileUploader;
import com.example.myapplication.model.Teacher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class signup1teacher extends AppCompatActivity {

    private static final  int PICK_IMAGE_REQUEST = 1;
    private ImageView imagenext;
    private ImageView imageback;
    public TextView textlist;
    private Intent i;
    private ImageView profilePict ;
    private ImageView addImagee ;
    private Teacher teacher ;
    static Dialog dialog;
    private FileUploader fileUploader;
    private Uri imageUri ;

    public TextView firstName;
    public TextView lastName;


    public void init(){
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        profilePict = findViewById(R.id.imageGroup);
        addImagee = findViewById(R.id.addImage);
        dialog = new Dialog(this);
        textlist = (TextView) findViewById(R.id.levels);
        imagenext = (ImageView) findViewById(R.id.nextsignup2teacherImage);
        imageback = (ImageView) findViewById(R.id.backmainteacherImage);
        teacher = new Teacher();

      //  fileUploader = new FileUploader(getApplicationContext() , "teachers-profile-images");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup1teacher);
        init();




        //EVENTS
        addImagee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();

            }
        });

        imagenext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  teacher.setProfilePicUrl( fileUploader.uploadFile(imageUri,progressBar));
                teacher.setFirstName(firstName.getText().toString());
                teacher.setLastName(lastName.getText().toString());

               //Toast.makeText(getApplicationContext(),teacher.toString(),Toast.LENGTH_LONG).show();

                i = new Intent(getApplicationContext(), signupteacher2.class);

                i.putExtra("teacher",teacher);
                startActivity(i);


            }
        });

        imageback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);

            }
        });


      textlist.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder builder = new AlertDialog.Builder(signup1teacher.this);
            String[] levelsArray = new String[]{"GI1","GI2","GI3"};
            final boolean[] checkedLevelsArray = new boolean[]{
                    true,
                    false,
                    false
            };

            final List<String> levelList = Arrays.asList(levelsArray);

            builder.setTitle("select Levels you teach plz");
            builder.setMultiChoiceItems(levelsArray, checkedLevelsArray,
                    new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                    checkedLevelsArray[which] = isChecked ;
                    String currentLevel = levelList.get(which);

                }
            });

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ArrayList<String> levels = new ArrayList<>();
                    for (int i =0 ; i<checkedLevelsArray.length;i++){
                        if(checkedLevelsArray[i]){
                          levels.add(levelList.get(i));
                        }
                    }
                    teacher.setLevels(levels);


                }
            });

            builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                }
            });

            AlertDialog dialog =builder.create();
            dialog.show();






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
            profilePict.setImageURI(imageUri);
         //   teacher.setProfilePicUrl( fileUploader.uploadFile(imageUri,progressBar));


        }
    }



}
