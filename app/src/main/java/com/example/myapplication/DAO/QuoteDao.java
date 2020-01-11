package com.example.myapplication.DAO;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.myapplication.model.Quote;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class QuoteDao {
    private DatabaseReference ref;
    private Context context;
    private Quote quote  ;

    public DatabaseReference getRef() {
        return ref;
    }

    public Context getContext() {
        return context;
    }

    public Quote getQuoteDao() {
        return quote;
    }



    public QuoteDao(final Context context) {
        Random random = new Random();
        int randomInt = random.nextInt(10);
        Toast.makeText(context,Integer.toString(randomInt),Toast.LENGTH_LONG).show();

        ref = FirebaseDatabase.getInstance().getReference().child("quotes").child(Integer.toString(randomInt));
        this.quote=new Quote();
        this.context = context;
    }


    public void getQuote(){


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                   String quot = dataSnapshot.getValue().toString();

                    QuoteDao.this.quote.setQuote(quot);

                }




            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
