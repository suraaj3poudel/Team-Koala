package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DatabaseSQLite extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "drivernotes.db";
    public static final String TABLE_NAME = "notes_data";
    public static final String COL1 = "ID";
    public static final String COL2 = "DIDE";
    public static final String COL3 = "NOTES";





    public DatabaseSQLite(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,DIDE TEXT, NOTES TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String id, String notes) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, id);
        contentValues.put(COL3, notes);


        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public Cursor getNotes(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String result;
        Cursor res;
        try {
            res = db.rawQuery("SELECT NOTES FROM " + TABLE_NAME + " WHERE DIDE = " + "\'" + id + "\'", null);
        }
        catch (Exception e){
           res = null;
        }
        return res;
    }
}
