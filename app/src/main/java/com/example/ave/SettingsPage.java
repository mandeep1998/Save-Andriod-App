package com.example.ave;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.ave.Database.DatabaseSql;

import java.util.ArrayList;

public class SettingsPage extends AppCompatActivity {
private ListView listView;
private Button logoutButton;
private ArrayList<String> list = new ArrayList<>();
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
        logoutButton=(Button)findViewById(R.id.logout_button) ;
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB.getAuth().signOut();
                finish();
                startActivity(new Intent (getApplicationContext(), mainLogin.class));
            }
        });

        listView=(ListView) findViewById(R.id.listView);
        fillList();
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item=listView.getItemAtPosition(position).toString();
                if(item=="Update Budget") {
                    startActivity(new Intent(getApplicationContext(), BudgetPop.class));
                }
            }
        });
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    public void fillList()
    {
        list.add("Update Budget");
        list.add("Notification Settings");
        list.add("Update Email");
        list.add("Change Username");
        list.add("Change Password");
    }
}
