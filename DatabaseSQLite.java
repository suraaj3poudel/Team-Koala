package com.example.localdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseSQLite extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Local_Database";

    public static final String TABLE_NAME_DRIVER = "driverTable";
    public static final String COL2 = "driverCode";
    public static final String COL3 = "driverName";

    public static final String TABLE_NAME_TRIP = "tripTable";
    public static final String COL4 = "tripId";
    public static final String COL5 = "tripName";

    public static final String TABLE_NAME_SITE = "siteTable";
    public static final String COL6 = "siteId";
    public static final String COL7 = "siteName";
    public static final String COL8 = "siteAddress";
    public static final String COL9 = "siteCity";
    public static final String COL10 = "siteState";
    public static final String COL11 = "siteZIP";

    public static final String TABLE_NAME_SOURCE = "sourceTable";
    public static final String COL12 = "sourceId";
    public static final String COL13 = "sourceName";
    public static final String COL14 = "sourceAddress";
    public static final String COL15 = "sourceCity";
    public static final String COL16 = "sourceState";
    public static final String COL17 = "sourceZIP";

    public static final String TABLE_NAME_TRUCK = "truckTable";
    public static final String COL18 = "truckId";
    public static final String COL19 = "truckCode";
    public static final String COL20 = "truckDesc";

    public static final String TABLE_NAME_FUEL = "fuelTable";
    public static final String COL21 = "fuelType";
    public static final String COL22 = "fuelAmount";

    public DatabaseSQLite(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDriverTable = "CREATE TABLE " + TABLE_NAME_DRIVER
                + " (driverCode TEXT PRIMARY KEY AUTOINCREMENT, driverName TEXT)";
        db.execSQL(createDriverTable);

        String createTripTable = "CREATE TABLE " + TABLE_NAME_TRIP + " (tripId TEXT PRIMARY KEY, tripName TEXT)";
        db.execSQL(createTripTable);

        String createSiteTable = "CREATE TABLE " + TABLE_NAME_SITE
                + " (siteId TEXT PRIMARY KEY, siteName TEXT, siteAddress TEXT, siteCity TEXT, siteState TEXT, siteZIP TEXT)";
        db.execSQL(createSiteTable);

        String createSourceTable = "CREATE TABLE " + TABLE_NAME_SOURCE
                + " (sourceId TEXT PRIMARY KEY, sourceName TEXT, sourceAddress TEXT, sourceCity TEXT, sourceState TEXT, sourceZIP TEXT)";
        db.execSQL(createSourceTable);

        String createTruckTable = "CREATE TABLE " + TABLE_NAME_TRUCK
                + " (truckId TEXT PRIMARY KEY, truckCode TEXT, truckDesc TEXT)";
        db.execSQL(createTruckTable);

        String createFuelTable = "CREATE TABLE " + TABLE_NAME_FUEL + " (fuelType TEXT PRIMARY KEY, fuelAmount TEXT)";
        db.execSQL(createFuelTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_DRIVER);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TRIP);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SITE);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SOURCE);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_TRUCK);
        onCreate(db);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_FUEL);
        onCreate(db);
    }

    public boolean addDataDriver(String driver_Code, String driver_Name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, driver_Code);
        contentValues.put(COL3, driver_Name);

        long result = db.insert(TABLE_NAME_DRIVER, null, contentValues);

        // if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addDataTrip(String trip_Id, String trip_Name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL4, trip_Id);
        contentValues.put(COL5, trip_Name);

        long result = db.insert(TABLE_NAME_TRIP, null, contentValues);

        // if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addDataSite(String site_Id, String site_Name, String site_Address, String site_City,
                               String site_State, String site_ZIP) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL6, site_Id);
        contentValues.put(COL7, site_Name);
        contentValues.put(COL8, site_Address);
        contentValues.put(COL9, site_City);
        contentValues.put(COL10, site_State);
        contentValues.put(COL11, site_ZIP);

        long result = db.insert(TABLE_NAME_SITE, null, contentValues);

        // if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addDataSource(String source_Id, String source_Name, String source_Address, String source_City,
                                 String source_State, String source_ZIP) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL12, source_Id);
        contentValues.put(COL13, source_Name);
        contentValues.put(COL14, source_Address);
        contentValues.put(COL15, source_City);
        contentValues.put(COL16, source_State);
        contentValues.put(COL17, source_ZIP);

        long result = db.insert(TABLE_NAME_SOURCE, null, contentValues);

        // if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addDataTruck(String truck_Id, String truck_Code, String truck_Desc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL18, truck_Id);
        contentValues.put(COL19, truck_Code);
        contentValues.put(COL20, truck_Desc);

        long result = db.insert(TABLE_NAME_TRUCK, null, contentValues);

        // if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addDataFuel(String fuel_Type, String fuel_Amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL21, fuel_Type);
        contentValues.put(COL22, fuel_Amount);

        long result = db.insert(TABLE_NAME_FUEL, null, contentValues);

        // if date as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    // To Retrieve data from the database
    public Cursor showDataDriver() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor retrieveDriverInfo = db.rawQuery("SELECT * FROM " + TABLE_NAME_DRIVER, null);

        db.close();
        return retrieveDriverInfo;
    }

    public Cursor showDataTrip() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor retrieveTripInfo = db.rawQuery("SELECT * FROM " + TABLE_NAME_TRIP, null);

        db.close();
        return retrieveTripInfo;
    }

    public Cursor showDataSite() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor retrieveSiteInfo = db.rawQuery("SELECT * FROM " + TABLE_NAME_SITE, null);

        db.close();
        return retrieveSiteInfo;
    }

    public Cursor showDataSource() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor retrieveSourceInfo = db.rawQuery("SELECT * FROM " + TABLE_NAME_SOURCE, null);

        db.close();
        return retrieveSourceInfo;
    }

    public Cursor showDataTruck() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor retrieveTruckInfo = db.rawQuery("SELECT * FROM " + TABLE_NAME_TRUCK, null);

        db.close();
        return retrieveTruckInfo;
    }

    public Cursor showDataFuel() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor retrieveFuelInfo = db.rawQuery("SELECT * FROM " + TABLE_NAME_FUEL, null);

        db.close();
        return retrieveFuelInfo;
    }

    // To delete data from the database
    public Cursor deleteDataDriver() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor deleteDriverInfo = db.rawQuery("DELETE  FROM " + TABLE_NAME_DRIVER, null);
        return deleteDriverInfo;
    }

    public Cursor deleteDataTrip() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor deleteTripInfo = db.rawQuery("DELETE  FROM " + TABLE_NAME_TRIP, null);
        return deleteTripInfo;
    }

    public Cursor deleteDataSite() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor deleteSiteInfo = db.rawQuery("DELETE  FROM " + TABLE_NAME_SITE, null);
        return deleteSiteInfo;
    }

    public Cursor deleteDataSource() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor deleteSourceInfo = db.rawQuery("DELETE  FROM " + TABLE_NAME_SOURCE, null);
        return deleteSourceInfo;
    }

    public Cursor deleteDataTruck() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor deleteTruckInfo = db.rawQuery("DELETE  FROM " + TABLE_NAME_TRUCK, null);
        return deleteTruckInfo;
    }

    public Cursor deleteDataFuel() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor deleteFuelInfo = db.rawQuery("DELETE  FROM " + TABLE_NAME_FUEL, null);
        return deleteFuelInfo;
    }

}
