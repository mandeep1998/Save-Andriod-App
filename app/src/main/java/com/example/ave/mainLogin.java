package com.example.ave;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ave.Database.DatabaseSql;

public class mainLogin extends AppCompatActivity implements View.OnClickListener {
    private Button btn_reg;
    private Button btn_login;
    private EditText Email;
    private EditText Password;
    private DatabaseSql Db = new DatabaseSql();//mine

    private ProgressDialog progressDialog;
   // private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        //firebaseAuth= firebaseAuth.getInstance();
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
        Db.signin(email,password);

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


            if(Db.register(email,password))
            {
                Toast.makeText(mainLogin.this,"Reg Successfully", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(mainLogin.this," Try again",Toast.LENGTH_SHORT).show();
            }
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
