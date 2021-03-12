package com.example.myapplication;

public class TripInfoClass {

    private String driverName;

    public TripInfoClass(){}

    public TripInfoClass(String driver){
        driverName = driver;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
}
