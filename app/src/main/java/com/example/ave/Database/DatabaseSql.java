package com.example.ave.Database;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.ave.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DatabaseSql extends AppCompatActivity {
   private FirebaseAuth firebaseAuth;
    private String email;
    private String password;
    private boolean check=false;
    private Boolean checkr;
    public DatabaseSql()
    {
        firebaseAuth= firebaseAuth.getInstance();

    }

    public FirebaseAuth getAuth()
    {
        return firebaseAuth;
    }
    public void signin(String email, String password)
    {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            finish();
                        }
                    }
                });
    }

    public Boolean register(String email,String password)
    {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                //progressDialog.dismiss();
                if(task.isSuccessful()){

                    //Toast.makeText(mainLogin.this,"Reg Successfully", Toast.LENGTH_SHORT).show();
                    checkr=true;
                } else {
                    //Toast.makeText(mainLogin.this," Try again",Toast.LENGTH_SHORT).show();
                    checkr=false;
                }
            }
        });
        return checkr;
    }
}
