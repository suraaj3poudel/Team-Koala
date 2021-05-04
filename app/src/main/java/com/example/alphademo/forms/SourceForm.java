package com.example.alphademo.forms;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
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
import com.example.alphademo.database.DatabaseSQLite;
import com.example.alphademo.database.DatabaseSQLiteForms;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Calendar;

public class SourceForm extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;

    //Initialize variable
    EditText date1,date2,fuelStickB, meterB, grossGall, netGall, fuelStickA, meterA;
    DatePickerDialog datePickerDialog;
    TextView time1,time2,fuelType,barcode;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    public static final String MyPREFERENCE = "TRIP_STATUS" ;
    //public static final String status = "not_complete";

    Button mCaptureBtn;
    ImageView mImageView;
    Uri image_uri;
    DatabaseSQLiteForms myDB;

    Button btScan, doneBtn;
    String id, data;
    boolean photo = false;
    String startDate,endDate,fuelReadingB,meterReadingB,startTime,endTime,grossGallon,netGallon,fuelReadingA,meterReadingA,barcodeNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String fuel = getIntent().getStringExtra("fuelType");
        setContentView(R.layout.pickup_form);
        sharedpreferences = getSharedPreferences(MyPREFERENCE, Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        //editor.putBoolean("tripComplete",false);
        myDB = new DatabaseSQLiteForms(this);

        id =  getIntent().getStringExtra("id");
        data = myDB.getData(id);

        //initiate variable for fuel type and set the value
        fuelType = findViewById(R.id.fuelType);
        fuelType.setText(fuel);

        // initiate variable for date picker
        date1 = findViewById(R.id.date1);
        date2 = findViewById(R.id.date2);

        /**perform click event on edit text for date */
        onClickDate1();
        onClickDate2();

        //initiate variable for time picker
        time1 = findViewById(R.id.startToEnd);
        time2 = findViewById(R.id.endToStart);

        /**perform click event on edit text for time */
        onClickTime1();
        onClickTime2();

        // initiate variable for fuel reading,meter reading, gross gallon and net gallon picked
        fuelStickB = findViewById(R.id.fuelStickB);
        meterB = findViewById(R.id.meterB);
        grossGall = findViewById(R.id.grossGall);
        netGall = findViewById(R.id.netGall);
        fuelStickA = findViewById(R.id.fuelStickA);
        meterA = findViewById(R.id.meterA);
        barcode = findViewById(R.id.scanText);

        if(!data.equals("")){
            try {
                String[] datas = data.split(",");
                date1.setText(datas[0]);
                date2.setText(datas[1]);
                fuelStickB.setText(datas[2]);
                meterB.setText(datas[3]);
                time1.setText(datas[4]);
                time2.setText(datas[5]);
                grossGall.setText(datas[6]);
                netGall.setText(datas[7]);
                fuelStickA.setText(datas[8]);
                meterA.setText(datas[9]);
            }
            catch (Exception e){

            }
        }

        myDB.addData(id,null);
        //initiate variable for camera
        mImageView = findViewById(R.id.image_view);
        mCaptureBtn = findViewById(R.id.button5);

        mCaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /**if system os is >= marshmallow, request for run time permission */
                photo = true;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED){
                        /**if permission not enabled, request for the permission */
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

                        /**popup msg to request permission */
                        requestPermissions(permission, PERMISSION_CODE);
                    }

                    else {
                        /**if permission is already granted */
                        openCamera();
                    }

                }
                else {
                    /**system os < marshmallow */
                    openCamera();
                }
            }
        });

        // Initialize variable for barcode scan
        btScan = findViewById(R.id.buttonScan);
        btScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(SourceForm.this);
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();
            }
        });

        //Close the form after all values has been entered
        doneBtn = findViewById(R.id.doneBtn);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateInput()){
                    editor.putString("status"+id, "complete").apply();
                    editor.commit();
                    //Log.i("MessageT",sharedpreferences.getString("status","work")+"");
                    finish();
                }
            }
        });



    }

    /**
     * method for start date picker
     *
     *
     */

    private void onClickDate1() {
        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                Log.i("Infor ", "Date1 Clicked");
                final Calendar cal = Calendar.getInstance();
                int mYear = cal.get(Calendar.YEAR); // current year
                int mMonth = cal.get(Calendar.MONTH); // current month
                int mDay = cal.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(SourceForm.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                String put = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                date1.setText(put);
                                myDB.updateData(id,date1.getText()+","+date2.getText()+","+fuelStickB.getText().toString()+","+meterB.getText().toString()
                                        +","+time1.getText()+","+time2.getText()+","+grossGall.getText().toString()+","+netGall.getText().toString()+","+fuelStickA.getText().toString()+","+ meterA.getText().toString());
                                //myDB.updateData(id,date1.getText()+","+date2.getText()+","+""+","+""+","+time1.getText()+","+time2.getText()+","+""+","+""+","+""+","+"");
                                //myDB.addData(id,put);
                            }
                        }, mYear, mMonth, mDay);
                //disable future date
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });
    }

    /**
     * method for end date picker
     */
    private void onClickDate2() {
        date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                Log.i("Infor ", "Date2 Clicked");
                final Calendar cal = Calendar.getInstance();
                int mYear = cal.get(Calendar.YEAR); // current year
                int mMonth = cal.get(Calendar.MONTH); // current month
                int mDay = cal.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(SourceForm.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                String put = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                date2.setText(put);
                                myDB.updateData(id,date1.getText()+","+date2.getText()+","+fuelStickB.getText().toString()+","+meterB.getText().toString()
                                        +","+time1.getText()+","+time2.getText()+","+grossGall.getText().toString()+","+netGall.getText().toString()+","+fuelStickA.getText().toString()+","+ meterA.getText().toString());
                                //myDB.addData(id,data);

                            }
                        }, mYear, mMonth, mDay);
                //disable past date
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });
    }

    /**method for start time picker
     *
     */
    public void onClickTime1(){
        time1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mCurrentTime = Calendar.getInstance();
                int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mCurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(SourceForm.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String put=String.format("%02d:%02d",selectedHour, selectedMinute);
                        time1.setText(put);
                        myDB.updateData(id,date1.getText()+","+date2.getText()+","+fuelStickB.getText().toString()+","+meterB.getText().toString()
                                +","+time1.getText()+","+time2.getText()+","+grossGall.getText().toString()+","+netGall.getText().toString()+","+fuelStickA.getText().toString()+","+ meterA.getText().toString());
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.show();
            }
        });
    }

    /**method for end time picker
     *
     */
    public void onClickTime2(){
        time2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar nCurrentTime = Calendar.getInstance();
                int hour = nCurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = nCurrentTime.get(Calendar.MINUTE);
                TimePickerDialog nTimePicker;
                nTimePicker = new TimePickerDialog(SourceForm.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        //time2.setText(String.format("%02d:%02d",selectedHour, selectedMinute));

                        String put=String.format("%02d:%02d",selectedHour, selectedMinute);
                        time2.setText(put);
                        myDB.updateData(id,date1.getText()+","+date2.getText()+","+fuelStickB.getText().toString()+","+meterB.getText().toString()
                                +","+time1.getText()+","+time2.getText()+","+grossGall.getText().toString()+","+netGall.getText().toString()+","+fuelStickA.getText().toString()+","+ meterA.getText().toString());
                    }
                }, hour, minute, true);//Yes 24 hour time
                nTimePicker.show();
            }
        });
    }


    /**
     *  openCamera method
     */

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


    /**handles permission result
     *
     * @param requestCode first parameter of the the method ononRequestPermissionsResult
     * @param permissions second parameter of the the method ononRequestPermissionsResult
     * @param grantResults third parameter of the the method ononRequestPermissionsResult
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        /**method is called when user chooses allow or deny from permission request popup */
        if (requestCode == PERMISSION_CODE) {//permission granted
            if (grantResults.length > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED) {

                openCamera();
            }
            //permission denied
            else {
                Toast.makeText(this, "Permission Denied...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setBarCode(){

    }


    /**
     * set the result
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //called when image captured from camera
        if(photo) {
            if (mImageView != null) {
                if (resultCode != RESULT_CANCELED) {
                    mImageView.setImageURI(image_uri);
                    photo = false;
                    return;
                }
            }
        }

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult.getContents() != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(SourceForm.this);
            //set Title
            builder.setTitle("Scanned Barcode");
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
        } else {
            Toast.makeText(getApplicationContext(), "Could not Scan anything... ", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method validates the data entered
     */
    public boolean validateInput(){
        try {
            boolean flag = false;
            //converting value entered to string
            startDate = date1.getText().toString();
            endDate = date2.getText().toString();
            fuelReadingB = fuelStickB.getText().toString();
            meterReadingB = meterB.getText().toString();
            startTime = time1.getText().toString();
            endTime = time2.getText().toString();
            grossGallon = grossGall.getText().toString();
            netGallon = netGall.getText().toString();
            fuelReadingA = fuelStickA.getText().toString();
            meterReadingA = meterA.getText().toString();
            barcodeNum = barcode.getText().toString();

            myDB.updateData(id,startDate+","+endDate+","+fuelReadingB+","+meterReadingB+","+startTime+","+endTime+","+grossGallon+","+netGallon+","+fuelReadingA+","+meterReadingA);


            if (startDate.equals("")) {
                Toast.makeText(this, "Please select Start Date", Toast.LENGTH_SHORT).show();
                date1.setError("Please select Start Date");
                date1.requestFocus();
            } else if (endDate.equals("")) {
                Toast.makeText(this, "Please select End Date", Toast.LENGTH_SHORT).show();
                date2.setError("Please select End Date");
                date2.requestFocus();
            } else if (fuelReadingB.equals("")) {
                Toast.makeText(this, "Please select Fuel Stick Reading Before", Toast.LENGTH_SHORT).show();
                fuelStickB.setError("Please select Fuel Stick Reading Before");
                fuelStickB.requestFocus();
            } else if (meterReadingB.equals("")) {
                Toast.makeText(this, "Please select Meter Reading Before", Toast.LENGTH_SHORT).show();
                meterB.setError("Please select Meter Reading Before");
                meterB.requestFocus();
            } else if (startTime.equals("")) {
                Toast.makeText(this, "Please select Start Time", Toast.LENGTH_SHORT).show();
                time1.setError("Please select Start Time");
                time1.requestFocus();
            } else if (endTime.equals("")) {
                Toast.makeText(this, "Please select End Time", Toast.LENGTH_SHORT).show();
                time2.setError("Please select End Time");
                time2.requestFocus();
            } else if (grossGallon.equals("")) {
                Toast.makeText(this, "Please select Gross Gallon Dropped", Toast.LENGTH_SHORT).show();
                grossGall.setError("Please select Gross Gallon Dropped");
                grossGall.requestFocus();
            } else if (netGallon.equals("")) {
                Toast.makeText(this, "Please select Net Gallon Dropped", Toast.LENGTH_SHORT).show();
                netGall.setError("Please select Net Gallon Dropped");
                netGall.requestFocus();
            } else if (fuelReadingA.equals("")) {
                Toast.makeText(this, "Please select Fuel Stick Reading After", Toast.LENGTH_SHORT).show();
                fuelStickA.setError("Please select Fuel Reading After");
                fuelStickA.requestFocus();
            }

            else if (Double.parseDouble(fuelReadingB) >= Double.parseDouble(fuelReadingA)) {
                Toast.makeText(this, "Fuel Stick Reading After is less than or equal to Fuel Stick Reading Before", Toast.LENGTH_SHORT).show();
                fuelStickA.setError("Fuel Reading After is less than Fuel Reading Before");
                fuelStickA.requestFocus();
            }

//        else if(!(TextUtils.isEmpty(fuelReadingB) && TextUtils.isEmpty(fuelReadingA))){
//            double fB = Double.parseDouble(fuelReadingB);
//            double fA = Double.parseDouble(fuelReadingA);
//
//            if(fB >= fA) {
//                Toast.makeText(this, "Fuel Reading After is less than Fuel Reading Before", Toast.LENGTH_SHORT).show();
//                fuelStickA.setError("Fuel Reading After is less than Fuel Reading Before");
//                fuelStickA.requestFocus();
//            }
//        }

            else if (meterReadingA.equals("")) {
                Toast.makeText(this, "Please select Meter Reading After", Toast.LENGTH_SHORT).show();
                meterA.setError("Please select Meter Reading After");
                meterA.requestFocus();
            }

            //        Changing String to Integer
//        int mB = Integer.parseInt(meterReadingB);
//        int mA = Integer.parseInt(meterReadingA);
//
//        double fB = Double.parseDouble(fuelReadingB);
//        double fA = Double.parseDouble(fuelReadingA);

            else if (Double.parseDouble(meterReadingB) >= Double.parseDouble(meterReadingA)) {
                Toast.makeText(this, "Meter Reading After is less than or equal to Meter Reading Before", Toast.LENGTH_SHORT).show();
                meterA.setError("Meter Reading After is less than Meter Reading Before");
                meterA.requestFocus();
            }

//        else if(!(TextUtils.isEmpty(meterReadingB) && TextUtils.isEmpty(meterReadingA))){
//            double mB = Double.parseDouble(meterReadingB);
//            double mA = Double.parseDouble(meterReadingA);
//
//            if(mB >= mA) {
//                Toast.makeText(this, "Meter Reading After is less than Meter Reading Before", Toast.LENGTH_SHORT).show();
//                meterA.setError("Meter Reading After is less than Meter Reading Before");
//                meterA.requestFocus();
//
//            }
//        }


            else {
                Log.i("ERRORE", "somethings wrong");
                flag = true;
                date1.setError(null);
                date2.setError(null);
                fuelStickB.setError(null);
                meterB.setError(null);
                time1.setError(null);
                time2.setError(null);
                grossGall.setError(null);
                netGall.setError(null);
                fuelStickA.setError(null);
                meterA.setError(null);
            }
            return flag;
        }
        catch (Exception e){

            return false;
        }
    }

}