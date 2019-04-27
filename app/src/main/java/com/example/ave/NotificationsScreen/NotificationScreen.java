package com.example.ave.NotificationsScreen;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.ave.DashboardScreen.DashboardScreen;
import com.example.ave.LoginAndMain.MainActivity;
import com.example.ave.R;
import com.example.ave.SettingsScreen.SettingsPage;

public class NotificationScreen extends AppCompatActivity {
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

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}
