package com.example.alphademo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button btLocation, btnadd, btnview;
    TextView textView1, textView2, textView3, textView4, textView5, textResult;
    FusedLocationProviderClient fusedLocationProviderClient;
    EditText textinput1, textinput2;
    DatabaseSQLite myDB;
    RadioGroup radiogroup;
    RadioButton radioButton;
    ProgressBar progressBar;

    List<Address> addresses = null;
    double clatitude, clongitude;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DatabaseSQLite(this);


        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        btLocation =  findViewById(R.id.btlocation);
        textView1 = findViewById(R.id.text_view1);
        textView2 = findViewById(R.id.text_view2);
        textView3 =  findViewById(R.id.text_view3);
        textView4 =  findViewById(R.id.text_view4);
        textView5 =  findViewById(R.id.text_view5);

        btnadd =   findViewById(R.id.add);
        btnview =   findViewById(R.id.view);

        textinput1 =  findViewById(R.id.input1);
        textinput2 =  findViewById(R.id.input2);

        radiogroup = findViewById(R.id.radioGroup);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this
        );

        locationCheck();

        btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationCheck();
            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(textinput1.getText().toString().equals("") || textinput2.getText().toString().equals("") || radioButton == null){
                        Toast.makeText(MainActivity.this, "Please Enter all the details", Toast.LENGTH_LONG).show();
                        return;
                    }

                    else if(clatitude ==0 || clongitude ==0) {
                        Toast.makeText(MainActivity.this, "Please get your Current Location by pressing the \"Get Location Button\"", Toast.LENGTH_LONG).show();
                        return;
                    }

                    boolean isInserted =  myDB.addData(textinput1.getText().toString(),
                            textinput2.getText().toString(), radioButton.getText().toString(),clatitude, clongitude );

                    if(isInserted){
                        Toast.makeText(MainActivity.this, "Data Entry Successful", Toast.LENGTH_LONG).show();
                    }

                    else{
                        Toast.makeText(MainActivity.this, "Data Entry Failed", Toast.LENGTH_LONG).show();
                    }
                }
            });


        btnview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor result = myDB.showData();
                if(result.getCount()==0){
                    return;
                }

                StringBuffer buffer  = new StringBuffer();
                while(result.moveToNext()){
                    buffer.append(result.getString(0) + ". ");
                    buffer.append("First Name: "  + result.getString(1)+  "\n");
                    buffer.append("    Last Name: " +result.getString(2) +  "\n");
                    buffer.append("    Gender: " + result.getString(3) +  "\n");
                    buffer.append("    Latitude: " + result.getString(4) +  "\n");
                    buffer.append("    Longitude: " + result.getString(5) +  "\n\n");
                }

                showMessage("Entries", buffer.toString());
            }
        });

    }

    private void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton("OK", null);
        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    public void checkButton(View v){
        int radioID = radiogroup.getCheckedRadioButtonId();

        radioButton = findViewById(radioID);
    }

    private void reset(){

        textView1.setText("");
        textView2.setText("");
        textView3.setText("");
        textView4.setText("");
        textView5.setText("");
        addresses = null;
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this
        );
    }

    private void locationCheck(){
        if (ActivityCompat.checkSelfPermission(MainActivity.this
                , Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            getLocation();

            progressBar.setVisibility(View.GONE);

        }else{
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            Toast.makeText(MainActivity.this, "Please enable your GPS", Toast.LENGTH_LONG).show();
        }
    }

    private void getLocation() {
        reset();
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if(location !=null){

                    try {
                        Geocoder geocoder = new Geocoder(MainActivity.this, Locale.getDefault());
                        addresses = geocoder.getFromLocation(
                                location.getLatitude(),location.getLongitude(),1);

                        clatitude = addresses.get(0).getLatitude();
                        clongitude = addresses.get(0).getLongitude();

                        textView1.setText(Html.fromHtml(
                               "<font color='#6200EE'><b>Latitude: </b><br></font>"+addresses.get(0).getLatitude()
                        ));

                        textView2.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Longitude: </b><br></font>"+addresses.get(0).getLongitude()
                        ));

                        textView3.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Country: </b><br></font>"+addresses.get(0).getCountryName()
                        ));

                        textView4.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>City: </b><br></font>"+addresses.get(0).getLocality()
                        ));

                        textView5.setText(Html.fromHtml(
                                "<font color='#6200EE'><b>Full Address: </b><br></font>"+addresses.get(0).getAddressLine(0)
                        ));

                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }



}
