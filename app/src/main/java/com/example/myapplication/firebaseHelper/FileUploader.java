package com.example.myapplication.firebaseHelper;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;


public class FileUploader {
    private StorageReference mStorageRef;
    private StorageReference  fileReference;
    private StorageReference  httpsReference;
    private Context context ;

    public StorageReference getmStorageRef() {
        return mStorageRef;
    }

    public StorageReference getFileReference() {
        return fileReference;
    }

    public StorageReference getHttpsReference() {
        return httpsReference;
    }

    public Context getContext() {
        return context;
    }

    public static Uri getUri() {
        return uri;
    }

    public String getUrl() {
        return url;
    }

    public static Uri uri;
    private String url;
    private Task<Uri> urlTask;

    public Task<Uri> getUrlTask() {
        return urlTask;
    }
    public UploadTask getUploadTask() {
        return uploadTask;
    }

    private UploadTask uploadTask;
	
	 public String uploadFile2(Uri uri) {
        if (uri != null) {
            fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(uri));
            uploadTask = fileReference.putFile(uri);
            urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        FileUploader.this.url = task.getResult().toString();
                        Toast.makeText(context,  task.getResult().getPath() , Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        return this.url;
    }

    public void deleteFile(String url){
        httpsReference = FirebaseStorage.getInstance().getReferenceFromUrl(url);
        httpsReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

            }
        });
    }
	
	
	public FileUploader(String  httpsRef) {
        httpsReference = FirebaseStorage.getInstance().getReferenceFromUrl(httpsRef);
    }
	
	 public void downloadFile(final String url){

        httpsReference = FirebaseStorage.getInstance().getReferenceFromUrl(url);
        httpsReference.getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                String intStorageDirectory = Environment.getExternalStoragePublicDirectory
                        (Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
                File myDir = new File(intStorageDirectory);


                String name = "0" + httpsReference.getName();
                myDir = new File(myDir, name);


                try{
                    FileOutputStream fos = new FileOutputStream(myDir);
                    fos.write(bytes);
                    fos.flush();
                    fos.close();
                }
                catch (Exception e){
                    Toast.makeText(context, "nop", Toast.LENGTH_LONG).show();

                }
            }

        });
    }

	
	
	 public String getNameReferenece(String httpsRef){
        httpsReference = FirebaseStorage.getInstance().getReferenceFromUrl(httpsRef);
        return httpsReference.getName();
    }


    public FileUploader(Context context, String location ) {
        mStorageRef = FirebaseStorage.getInstance().getReference(location);
        this.context=context;
    }


    private String getFileExtension(Uri uri ) {

        ContentResolver cR = context.getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }



    public void uploadFile(Uri uri , final ProgressDialog p ) {
        p.setProgress(0);
        p.setMax(100);
        if (uri != null) {
            fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(uri));

            fileReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                          fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                              @Override
                                public void onSuccess(Uri uri) {
                                //  Toast.makeText(context,  uri.toString(), Toast.LENGTH_SHORT).show();
                                  FileUploader.uri= uri;

                                //  Toast.makeText(context, " this is url " + url, Toast.LENGTH_SHORT).show();

                                }
                            });

                          // url=taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();

                          // Toast.makeText(context, "File Uploaded "+" "+ url, Toast.LENGTH_LONG).show();

                        }

                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override

                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context , "failed to upload", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());

                    p.setMessage("Loding Data....");
                    p.setCancelable(false);
                    p.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

                   p.setProgress((int)progress);
                   if (p.getProgress()==100){
                       p.dismiss();
                   }else{
                       p.show();
                   }

                }
            });

        } else {
            Toast.makeText(context, "No file selected", Toast.LENGTH_SHORT).show();
        }

    }


}



