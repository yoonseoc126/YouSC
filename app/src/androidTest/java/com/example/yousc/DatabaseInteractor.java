package com.example.yousc;
import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import androidx.annotation.NonNull;
import java.util.HashMap;
import java.util.Map;



import com.google.android.gms.tasks.OnSuccessListener;

public class DatabaseInteractor {
    private User testUser;
    private FirebaseFirestore db;
    private DocumentReference userData;


    public DatabaseInteractor(FirebaseFirestore firestore) {
        db = firestore;
        testUser = new User();
    }

    public void addUser(String userEmail, String userPassword, OnSuccessListener<Void> onSuccessListener) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        }
                    }
                });
    }

    public void addEvent(String event, String location, String date, String time, String details) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userEmail = user.getEmail();
        Event newEvent = new Event(event, location, date, time, details, 0, 0, userEmail);
        mDatabase.child("events").push().setValue(newEvent);
    }

    public void getEvent() {

    }

}