package com.example.localdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;


public class dataBase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mylist.db";
    public static final String TABLE_NAME = "mylist_data";
    public static final String COL1 = "ID";
    public static final String COL2 = "FNAME";
    public static final String COL3 = "LNAME";
    public static final String COL4 = "GENDER";
    public static final String COL5 = "LAT";
    public static final String COL6 = "LON";

    public static final String Driver_Info = "Driver_Info";
    private String Fuel_Table;
    private String Site_Table;
    private String Source_Table;
    private String Trip_Table;
    private String Truck_Table;

    private String Driver_id;
    private String Driver_Name;

    private String Fuel_Amount;
    private String Fuel_Name;

    private String Site_ZIP;
    private String Site_State;
    private String Site_City;
    private String Site_Address;
    private String Site_Code;

    private String Source_ZIP;
    private String Source_State;
    private String Source_City;
    private String Source_Address;
    private String Source_Code;

    private String Trip_Name;
    private String Trip_ID;
    private String Truck_Name;
    private String Truck_ID;


    public dataBase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public dataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
