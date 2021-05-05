package com.example.alphademo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alphademo.views.setting.SettingFragment;
import com.example.alphademo.views.triplist.Trip_listFragment;
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

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.notification);
                dialog.setTitle("Title...");

                /**
                 * set the custom dialog components - text, image and button
                  */
                TextView text = (TextView) dialog.findViewById(R.id.text);
                text.setText("Here is your notification!");
                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);

                /**
                 * if button is clicked, close the custom dialog
                 */
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
        /**
         * setting the default fragment for MainActivity2
         * Adding slide animation to the fragment
         */
        getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enterlefttoright,R.anim.exitlefttoright).replace(R.id.fragment_container, new ViewAllTripsFragment()).commit();
    }

    /**
     * creating a listener for bottom navigation
     */
    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelected = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        /**
         *creating a listener to change the view fragments based on click
         */
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment select = null;
            switch (item.getItemId())
            {
                case R.id.trip_list:
                    select = new ViewAllTripsFragment() ;
                    break;
                case R.id.map:
                    select = new MapTemp();
                    break;
                case R.id.setting:
                    select = new SettingFragment();
                    break;
            }
            /**
             * displaying the view when clicked with animation
             */
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.enterrighttoleft, R.anim.exitrighttoleft).replace(R.id.fragment_container,  select).addToBackStack(null).commit();
            return true;
        }
    };


}






