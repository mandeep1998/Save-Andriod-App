package com.example.ave;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class NotificationScreen extends AppCompatActivity {
    private ListView view;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    return true;
                case R.id.navigation_dashboard:
                    startActivity(new Intent(getApplicationContext(), DashboardScreen.class));
                    return true;
                case R.id.navigation_notifications:
                    //leave empty
                    return true;
                case R.id.navigation_settings:
                    startActivity(new Intent (getApplicationContext(), SettingsPage.class));
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notificationscreen_layout);
        String itemlist[]= {"Welcome to $ave!!","Learn more on how to increase your score"};
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        view=(ListView)findViewById(R.id.view);
        ListAdapter fadapter= new ArrayAdapter<String>(NotificationScreen.this, android.R.layout.simple_list_item_1,itemlist);

        view.setAdapter(fadapter);

    }
}
