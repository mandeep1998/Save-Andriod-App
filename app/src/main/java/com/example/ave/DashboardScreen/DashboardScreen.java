package com.example.ave.DashboardScreen;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.ave.LoginAndMain.MainActivity;
import com.example.ave.NotificationsScreen.NotificationScreen;
import com.example.ave.R;
import com.example.ave.SettingsScreen.SettingsPage;
import com.razerdp.widget.animatedpieview.AnimatedPieView;
import com.razerdp.widget.animatedpieview.AnimatedPieViewConfig;
import com.razerdp.widget.animatedpieview.callback.OnPieSelectListener;
import com.razerdp.widget.animatedpieview.data.IPieInfo;
import com.razerdp.widget.animatedpieview.data.SimplePieInfo;

public class DashboardScreen extends AppCompatActivity {
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    return true;
                case R.id.navigation_dashboard:
                    //leave empty
                    return true;
                case R.id.navigation_notifications:
                    startActivity(new Intent(getApplicationContext(), NotificationScreen.class));
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

        setContentView(R.layout.dashboard_layout);
        AnimatedPieView animatedPieView = findViewById(R.id.pieView);
        AnimatedPieViewConfig config = new AnimatedPieViewConfig();
        config.addData(new SimplePieInfo(2500, Color.parseColor("#AAFF0000"), "Item1"));
        config.addData(new SimplePieInfo(3250, Color.parseColor("#AA00FF00"), "Item2"));
        config.addData(new SimplePieInfo(4500, Color.parseColor("#AA0000FF"), "Item3"));
        config.duration(1500);
        config.drawText(true);
        config.strokeMode(false);
        config.textSize(40);
        config.selectListener(new OnPieSelectListener<IPieInfo>() {

            @Override
            public void onSelectPie(@NonNull IPieInfo pieInfo, boolean isFloatUp) {
                Toast.makeText(DashboardScreen.this, pieInfo.getDesc() + "- " + pieInfo.getValue(),Toast.LENGTH_SHORT).show();

                BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
                navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
            }
        });

        config.startAngle(-180);

        animatedPieView.applyConfig(config);
        animatedPieView.start();
    }}