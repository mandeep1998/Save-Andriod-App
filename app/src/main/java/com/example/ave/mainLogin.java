package com.example.ave;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class mainLogin extends AppCompatActivity implements View.OnClickListener {
    private Button btn_reg;
    private Button btn_login;
    private EditText Email;
    private EditText Password;


    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        firebaseAuth= firebaseAuth.getInstance();
        findViewById(R.id.btn_reg).setOnClickListener(this);


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
            // emty
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

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }

                    }
                });
    }
        private void registerUser(){
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            // emty
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
            if(TextUtils.isEmpty(password)){
                // pass emty
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
                return;
            }
            progressDialog.setMessage("Registering plz wait");
            progressDialog.show();


            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            if(task.isSuccessful()){
                                Toast.makeText(mainLogin.this,"Reg Successfully", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(mainLogin.this," Try again",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
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
}
