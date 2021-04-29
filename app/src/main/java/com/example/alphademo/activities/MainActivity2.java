package com.example.alphademo.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.alphademo.MapTemp;
import com.example.alphademo.R;
import com.example.alphademo.views.setting.SettingFragment;
import com.example.alphademo.views.triplist.ViewAllTripsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity2 extends AppCompatActivity  {


    ImageView notification;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        notification = findViewById(R.id.bell);
        //notification.setOnClickListener(this);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.notification);
                dialog.setTitle("Title...");

                // set the custom dialog components - text, image and button
                TextView text = (TextView) dialog.findViewById(R.id.text);
                text.setText("Here is your notification!");


                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelected);
        bottomNavigationView.setActivated(true);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ViewAllTripsFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        Fragment home = null;
        if(getSupportFragmentManager().findFragmentByTag("TripList")!=null) {
            home = getSupportFragmentManager().findFragmentByTag("TripList");
        }
        FragmentManager fm = getFragmentManager();
//        if (fm.getBackStackEntryCount() > 1) {
//            //Log.i("MainActivity", "popping backstack");
//            fm.popBackStack();
//        } else {
//            //Log.i("MainActivity", "nothing on backstack, calling super");
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_HOME);
//            startActivity(intent);
//        }


        if (home != null) {
            if (!(home instanceof ViewAllTripsFragment) && home.isVisible()) {
                //Log.i("waht",getSupportFragmentManager().getBackStackEntryCount()+"");
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                    Log.i("waht", getSupportFragmentManager().getBackStackEntryCount() + "");
                } else {
                    super.onBackPressed();
                }
            }
        }else {
                //Primary fragment
                moveTaskToBack(true);
            }



    }



    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelected = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment select = null;
            switch (item.getItemId())
            {
                case R.id.trip_list:
                    select = new ViewAllTripsFragment() ;
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, select).commit();
                    break;
                case R.id.map:
                  if(getSupportFragmentManager().findFragmentByTag("map") == null) {
                       select = new MapTemp();
                      getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, select).commit();
                   }
                    else {
                      select = (Fragment) getSupportFragmentManager().findFragmentByTag("map");
                      FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                      //ft.remove(select);
                      //ft.add(R.id.fragment_container,select,"map");
                      ft.commit();
                  }
                    break;
                case R.id.setting:
                    select = new SettingFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, select).commit();
                    break;
            }



            return true;
        }


    };

    private View.OnLayoutChangeListener layoutChangeListener = new View.OnLayoutChangeListener(){

        @Override
        public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {

        }
    };







        }


