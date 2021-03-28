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
    //public static final String COL1 = "ID";
    public static final String COL1 = "DIDE";
    public static final String COL2 = "NOTES";





    public DatabaseSQLite(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (DIDE TEXT PRIMARY KEY, NOTES TEXT)";
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
        contentValues.put(COL1, id);
        contentValues.put(COL2, notes);


        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }
    public String getNotes(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] colms = {"DIDE","NOTES"};
        String result="";
        Cursor res;
        try {
            res= db.rawQuery("SELECT NOTES FROM " + TABLE_NAME + " WHERE DIDE = " + "\'" + id + "\'",null);
            while(res.moveToNext()) {
                //res.moveToFirst();
                int index = res.getColumnIndexOrThrow("NOTES");
                result= res.getString(index);
                //db.execSQL("UPDATE " + TABLE_NAME + " SET NOTES = " + notes + " WHERE DIDE = " + "\'" + id + "\'");
            }
        }
        catch (Exception e){
           result = null;
        }
        return result;
    }

    public void updateNotes(String id, String notes){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME + " SET NOTES = " +"\'" + notes +"\'" + " WHERE DIDE = " + "\'" + id + "\'");
    }
}
