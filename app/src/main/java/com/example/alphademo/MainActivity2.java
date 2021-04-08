package com.example.alphademo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity2 extends AppCompatActivity  {

    private DrawerLayout  drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelected);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TripFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelected = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment select = null;
            switch (item.getItemId())
            {
                case R.id.trip:
                    select = new TripFragment();
                    break;
                case R.id.profile:
                    select = new ProfileFragment();
                    break;
                case R.id.setting:
                    select = new SettingFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,  select).commit();
            return true;
        }
    };



}