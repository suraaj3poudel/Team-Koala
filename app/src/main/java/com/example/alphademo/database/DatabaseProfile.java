package com.example.alphademo.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import org.json.JSONObject;

public class DatabaseProfile extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "JsonProfile.db";
    public static final String TABLE_NAME = "Profile_Data";
    public static final String COL2 = "ID";
    public static final String COL3 = "NAME";
    public static final String COL4= "ADDRESS";
    public static final String COL5 = "EMAIL";
    public static final String COL6 = "PHONE";
    public static final String COL7 = "GENDER";



    public DatabaseProfile(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY, NAME TEXT, ADDRESS TEXT, EMAIL TEXT, PHONE TEXT, GENDER TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(Integer id,String name, String address, String email, String phone, String gender) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, id);
        contentValues.put(COL3, name);
        contentValues.put(COL4, address);
        contentValues.put(COL5, email);
        contentValues.put(COL6, phone);
        contentValues.put(COL7, gender);
        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public boolean addName(Integer id,String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, id);
        contentValues.put(COL3, name);
        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public String getData(Integer id, String field){
        SQLiteDatabase db = this.getWritableDatabase();
        String result="";
        Cursor res;
        try {
            res= db.rawQuery("SELECT " + field + " FROM " + TABLE_NAME + " WHERE ID = " + "\'" + id + "\'",null);
            while(res.moveToNext()) {
                //res.moveToFirst();
                int index = res.getColumnIndexOrThrow(field);
                result= res.getString(index);
                //db.execSQL("UPDATE " + TABLE_NAME + " SET NOTES = " + notes + " WHERE DIDE = " + "\'" + id + "\'");
            }
        }
        catch (Exception e){
            result = null;
        }
        return result;
    }

    public void updateInfo(Integer id, String name, String address, String email, String phone, String gender){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME + " SET NAME = " +"\'" + name +"\'" +", " + "ADDRESS = " + "\'" + address + "\'"+", " + "EMAIL = " + "\'" + email + "\'"+", " + "PHONE = " + "\'" + phone + "\'"+", " + "GENDER = " + "\'" + gender + "\'" + " WHERE ID = " + "\'" + id + "\'");
    }
}
//    public Cursor showData(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
//        return res;
//    }
