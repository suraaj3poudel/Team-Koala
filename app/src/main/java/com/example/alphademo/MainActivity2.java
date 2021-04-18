package com.example.alphademo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.alphademo.views.map.MapFragment;
import com.example.alphademo.views.setting.SettingFragment;
import com.example.alphademo.views.triplist.Trip_listFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity2 extends AppCompatActivity  {


    ImageView notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        notification = findViewById(R.id.bell);
        onclicknotify();


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelected);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Trip_listFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelected = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment select = null;
            switch (item.getItemId())
            {
                case R.id.trip_list:
                    select = new Trip_listFragment();
                    break;
                case R.id.map:
                    select = new MapFragment();
                    break;
                case R.id.setting:
                    select = new SettingFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,  select).commit();
            return true;
        }
    };

    public void onclicknotify(){
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new NotificationFragment()).commit();
            }
        });
    }
}