package com.example.ave.LoginAndMain;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ave.DashboardScreen.DashboardScreen;
import com.example.ave.NotificationsScreen.NotificationScreen;
import com.example.ave.R;
import com.example.ave.SettingsScreen.SettingsPage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mTextMessage;
    private FirebaseAuth firebaseAuth;
    private Button Logout_btn;
    private ListView transactionView;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                   //Nothing here because we are on main page
                    //leave blank so user cant spam home button
                    return true;
                case R.id.navigation_dashboard:
                  startActivity(new Intent(getApplicationContext(), DashboardScreen.class));
                    return true;
                case R.id.navigation_notifications:
                    startActivity(new Intent(getApplicationContext(), NotificationScreen.class));
                    return true;
                case R.id.navigation_settings:
                    startActivity(new Intent (getApplicationContext(), SettingsPage.class));
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() == null){
            startActivity(new Intent(this, mainLogin.class));
        }
        FirebaseUser user= firebaseAuth.getCurrentUser();
       // Logout_btn = (Button) findViewById(R.id.Logout_btn);
//        Logout_btn.setOnClickListener(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    public void onClick(View v) {
if (v == Logout_btn){
    firebaseAuth.signOut();
    finish();
    startActivity(new Intent(this, mainLogin.class));

}
    }
}
