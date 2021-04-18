package com.example.alphademo;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DatabaseJson extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "JsonObject.db";
    public static final String TABLE_NAME = "ObjectStore_data";
    public static final String COL1 = "ID";
    public static final String COL2 = "JSONOBJECT";






    public DatabaseJson(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY, JSONOBJECT TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(Integer id,String jsonObject) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL1, id);
        contentValues.put(COL2, jsonObject);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public JSONObject getObject(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String[] colms = {"ID","JSONOBJECT"};
        JSONObject jsonObject= new JSONObject();
        String result="";
        Cursor res;
        try {
            res= db.rawQuery("SELECT JSONOBJECT FROM " + TABLE_NAME + " WHERE ID = " + "\'" + id + "\'",null);
            while(res.moveToNext()) {
                //res.moveToFirst();
                int index = res.getColumnIndexOrThrow("JSONOBJECT");
                result= res.getString(index);
                jsonObject = new JSONObject(result);
                //db.execSQL("UPDATE " + TABLE_NAME + " SET NOTES = " + notes + " WHERE DIDE = " + "\'" + id + "\'");
            }
        }
        catch (Exception e){
            result = null;
        }
        return jsonObject;
    }

    /*public void updateNotes(String id, String notes){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME + " SET NOTES = " +"\'" + notes +"\'" + " WHERE DIDE = " + "\'" + id + "\'");
    }*/
}
//    public Cursor showData(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
//        return res;
//    }

