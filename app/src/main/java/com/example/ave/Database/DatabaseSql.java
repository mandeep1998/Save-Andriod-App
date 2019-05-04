package com.example.ave.Database;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.ave.MainActivity;
import com.example.ave.mainLogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class DatabaseSql extends AppCompatActivity {
   private FirebaseAuth firebaseAuth;
   private FirebaseFirestore firestore;
   private mainLogin login;
    private String email;
    private String password;
    private boolean check=false;
    private boolean checkr=false;

    public DatabaseSql()
    {
        firebaseAuth= firebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

    }

    public FirebaseAuth getAuth()
    {
        return firebaseAuth;
    }
    public FirebaseFirestore getfire() {return firestore;}





}
