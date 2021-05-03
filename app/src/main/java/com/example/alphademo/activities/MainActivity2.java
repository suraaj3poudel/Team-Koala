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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity  {


    ImageView notification;
    final Context context = this;
    int currentSelectedItemId = 0;

    public MainActivity2() throws IOException {
    }


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
//


        if (home != null) {
            if (!(home instanceof ViewAllTripsFragment) && home.isVisible()) {
                //Log.i("waht",getSupportFragmentManager().getBackStackEntryCount()+"");
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                } else {
                    super.onBackPressed();
                }
            }
        }else {
                //Primary fragment
                moveTaskToBack(true);
            }
    }

    Fragment map =  new MapTemp();

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelected = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment select = null;
            switch (item.getItemId())
            {
                case R.id.trip_list:
                    //select = new ViewAllTripsFragment() ;
//                    getSupportFragmentManager()
//                            .beginTransaction()
//                            .show(select).hide(new MapTemp())
//                            .commit();
                    swapFragments(new ViewAllTripsFragment(),item.getItemId(),"TRIPS");
                    break;
                case R.id.map:
                    select = map;
                    swapFragments(map,item.getItemId(),"MAP");
                    break;
                case R.id.setting:
                    select = new SettingFragment();
                    swapFragments(new SettingFragment(),item.getItemId(),"SETTING");
                    break;
            }


            //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, select).commit();
            return true;
        }


    };

    private void swapFragments(Fragment fragment, int itemId, String tag) {
        if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
            saveFragmentState(itemId, tag);
            createFragment(fragment, itemId, tag);
        }
    }

    Map<Integer, Fragment.SavedState> fragmentStateArray = new HashMap<Integer, Fragment.SavedState>();

    private void saveFragmentState(int itemId, String tag) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (currentFragment != null) {
            fragmentStateArray.put(currentSelectedItemId, getSupportFragmentManager().saveFragmentInstanceState(currentFragment));
        }

        currentSelectedItemId = itemId;
    }

    private void createFragment(Fragment fragment, int itemId, String tag) { fragment.setInitialSavedState(fragmentStateArray.get(itemId));

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment, tag)
                .commit();
    }







        }


