package com.example.alphademo.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseSQLiteForms extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "forms.db";
    public static final String TABLE_NAME = "forms";
    public static final String COL2 = "IDS";
    public static final String COL3 = "DATA";


    public DatabaseSQLiteForms(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (IDS TEXT PRIMARY KEY, DATA TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String id,String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, id);
        contentValues.put(COL3, data);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public String getData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] colms = {"IDS","DATA"};
        String result="";
        Cursor res;
        try {
            res= db.rawQuery("SELECT DATA FROM " + TABLE_NAME + " WHERE IDS = " + "\'" + id + "\'",null);
            while(res.moveToNext()) {
                //res.moveToFirst();
                int index = res.getColumnIndexOrThrow("DATA");
                result= res.getString(index);
                //db.execSQL("UPDATE " + TABLE_NAME + " SET NOTES = " + notes + " WHERE DIDE = " + "\'" + id + "\'");
            }
        }
        catch (Exception e){
            result = null;
        }
        return result;
    }

    public void updateData(String id, String data){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME + " SET DATA = " +"\'" + data +"\'" + " WHERE IDS = " + "\'" + id + "\'");
    }
//    public Cursor showData(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
//        return res;
//    }
}