package com.example.ave.SettingsScreen;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.ave.DashboardScreen.DashboardScreen;
import com.example.ave.LoginAndMain.MainActivity;
import com.example.ave.NotificationsScreen.NotificationScreen;
import com.example.ave.R;

import java.util.ArrayList;

public class SettingsPage extends AppCompatActivity {
private RecyclerView settingsView;
private settingsAdapter viewAdapter;
private RecyclerView.LayoutManager viewManager;
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


        settingsView=(RecyclerView) findViewById(R.id.recyclerView);
        //settingsView.hasFixedSize();
        settingsView.setLayoutManager(new LinearLayoutManager(this));
        //viewManager= new LinearLayoutManager(this);
        //settingsView.setLayoutManager(viewManager);

        viewAdapter = new settingsAdapter(list, this);
        settingsView.setAdapter(viewAdapter);

        fillList();
        System.out.println("working");
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void fillList()
    {
        list.add("Update Budget");
        viewAdapter.notifyDataSetChanged();
    }
}
