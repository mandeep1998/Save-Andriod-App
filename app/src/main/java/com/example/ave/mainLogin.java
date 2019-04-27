package com.example.ave;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ave.Database.DatabaseSql;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;

public class mainLogin extends AppCompatActivity implements View.OnClickListener {
    private Button btn_reg;
    private Button btn_login;
    private EditText Email;
    private EditText Password;
    private FirebaseAuth auth;
    private DatabaseSql Db = new DatabaseSql();//mine

    private ProgressDialog progressDialog;
   // private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        //firebaseAuth= firebaseAuth.getInstance();
        findViewById(R.id.btn_reg).setOnClickListener(this);

        auth=Db.getAuth();
        progressDialog = new ProgressDialog(this);
        btn_reg = (Button) findViewById(R.id.btn_reg);
        btn_login = (Button) findViewById(R.id.btn_login);

        Email = (EditText) findViewById(R.id.Email);
        Password = (EditText) findViewById(R.id.Password);

        btn_reg.setOnClickListener(this);
        btn_login.setOnClickListener(this);

    }

    private void userLogin(){
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            // empty
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(password)){
            // pass emty
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Logging In");
        progressDialog.show();
        signin(email,password);

        if(!(Db.getAuth().getCurrentUser()==null))
        {
            progressDialog.dismiss();//my own
            startActivity(new Intent(getApplicationContext(), MainActivity.class));//my own
        }
        else
        {
           Toast.makeText(this, "Invalid login",Toast.LENGTH_SHORT).show();
        }


    }
        private void registerUser(){
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            // empty
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
            if(TextUtils.isEmpty(password)){
                // pass empty
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
                return;
            }
            progressDialog.setMessage("Registering plz wait");
            progressDialog.show();


            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //progressDialog.dismiss();
                    String temp=("Check value "+ task.isSuccessful());
                    Log.e("TAG", temp);

                    if(task.isSuccessful())
                    {
                        progressDialog.dismiss();
                        Toast.makeText(mainLogin.this,"Reg Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        progressDialog.dismiss();
                        Toast.makeText(mainLogin.this," Try again",Toast.LENGTH_SHORT).show();
                    }

                }
            });
            write(email);
        }
    @Override
    public void onClick(View view){
        if(view == btn_reg){
            registerUser();


        }
        if(view == btn_login) {
            userLogin();

            // open login activity

        }
    }
    public void signin(String email, String password)
    {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            finish();
                        }
                    }
                });
    }

    public void write(String email)
    {
        HashMap<String,String> hash= new HashMap<>();
        hash.put("EmailID",email);

        Db.getfire().collection("Users").document(email).set(hash).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.e("TAG","Successfully written");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("TAG","error");
            }
        });
    }

}
