package com.example.ave;

import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ave.Database.DatabaseSql;

import java.util.ArrayList;
import java.util.HashMap;

public class SettingsPage extends AppCompatActivity {

private Button logoutButton;
private Button monthlyB;
private Button weeklyB;
private EditText budgetInput;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    return true;
                case R.id.navigation_dashboard:
                    startActivity((new Intent(getApplicationContext(), DashboardScreen.class)));
                    return true;
                case R.id.navigation_notifications:
                    startActivity(new Intent(getApplicationContext(), NotificationScreen.class));
                    return true;
                case R.id.navigation_settings:
                    //leave empty
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_page);
        final DatabaseSql DB= new DatabaseSql();
        weeklyB=(Button)findViewById(R.id.weeklyB);
        monthlyB=(Button) findViewById(R.id.monthlyB);
        budgetInput=(EditText)findViewById(R.id.editText);

        logoutButton=(Button)findViewById(R.id.logout_button) ;
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB.getAuth().signOut();
                finish();
                startActivity(new Intent (getApplicationContext(), mainLogin.class));
            }
        });

        weeklyB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String budget=budgetInput.getText().toString();
                Log.e("Budget",budget);
                String weekly="weekly";
                String email=DB.getAuth().getCurrentUser().getEmail();
                HashMap hash= new HashMap();
                hash.put("Budget",budget);
                hash.put("Type",weekly);
                if(!(budget==null))
                {
                    DB.getfire().collection(email).document("Budget").set(hash);
                }
                else
                {
                    Toast.makeText(SettingsPage.this,"Please enter a budget", Toast.LENGTH_SHORT).show();
                }
            }
        });

        monthlyB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String budget=budgetInput.getText().toString();
                String monthly="monthly";
                String email=DB.getAuth().getCurrentUser().getEmail();
                HashMap hash= new HashMap();
                hash.put("Budget",budget);
                hash.put("Type",monthly);
                if(!(budget==null))
                {
                    DB.getfire().collection(email).document("Budget").set(hash);
                    Toast.makeText(SettingsPage.this,"Budget has been added!",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(SettingsPage.this,"Please enter a budget before clicking", Toast.LENGTH_SHORT).show();
                }
            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }


}
