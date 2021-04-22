package com.example.alphademo.dummy;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.alphademo.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Calendar;

public class MainActivity4 extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;

    //Initialize variable
    EditText date;
    DatePickerDialog datePickerDialog;
    TextView time1,time2;

    Button mCaptureBtn;
    ImageView mImageView;
    Uri image_uri;

    Button btScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        // initiate variable for date picker
        date = (EditText) findViewById(R.id.date1);

        // perform click event on edit text
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar cal = Calendar.getInstance();
                int mYear = cal.get(Calendar.YEAR); // current year
                int mMonth = cal.get(Calendar.MONTH); // current month
                int mDay = cal.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(MainActivity4.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                date.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                //disable future date
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

                datePickerDialog.show();
            }
        });

        //initiate variable for time picker
        time1 = findViewById(R.id.startToEnd);
        time2 = findViewById(R.id.endToStart);

        time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mCurrentTime = Calendar.getInstance();
                int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mCurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MainActivity4.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time1.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.show();
            }
        });

        time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar nCurrentTime = Calendar.getInstance();
                int hour = nCurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = nCurrentTime.get(Calendar.MINUTE);
                TimePickerDialog nTimePicker;
                nTimePicker = new TimePickerDialog(MainActivity4.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time2.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                nTimePicker.show();
            }
        });

        //initiate variable for camera
        mImageView = findViewById(R.id.image_view);
        mCaptureBtn = findViewById(R.id.button5);

        mCaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if system os is >= marshmallow, request for run time permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED){
                        //if permission not enabled, request for the permission
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                        //popup msg to request permission
                        requestPermissions(permission, PERMISSION_CODE);
                    }

                    else {
                        // if permission is already granted
                        openCamera();
                    }

                }
                else {
                    // system os < marshmallow
                    openCamera();
                }
            }
        });

        // Initialize variable for barcode scan
        btScan = findViewById(R.id.buttonScan);
        btScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(MainActivity4.this);
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();
            }
        });

        Button continueBtn = findViewById(R.id.continueBtn);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity4.this, SignaturePop.class));
            }
        });
    }

    // openCamera method
    private void openCamera() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        //Camera intent
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);


    }

    // handles permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //method is called when user chooses allow or deny from permission request popup
        switch (requestCode){
            case PERMISSION_CODE:{
                //permission granted
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED){

                    openCamera();
                }
                //permission denied
                else {
                    Toast.makeText(this, "Permission Denied...",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    // set the result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //called when image captured from camera
        if (resultCode == RESULT_OK){

            mImageView.setImageURI(image_uri);
        }


        // Initialize intent result
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (intentResult.getContents() != null){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity4.this);
            //set Title
            builder.setTitle("Result");
            //set Message
            builder.setMessage(intentResult.getContents());

            TextView scanText = findViewById(R.id.scanText);
            scanText.setText(intentResult.getContents());

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }
        else{
            Toast.makeText(getApplicationContext(), "Could not Scan anything... ",Toast.LENGTH_SHORT).show();
        }


    }

}