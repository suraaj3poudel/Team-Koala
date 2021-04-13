package com.example.localdatabase;

public class Driver {

    // Attributes
    String driverName;
    String driverCode;

    // Empty Constructor
    public Driver() {

    }

    /**
     *
     * @param driverName Name of the driver
     * @param driverCode Code of the driver
     */
    public Driver(String driverName, String driverCode) {
        this.driverName = driverName;
        this.driverCode = driverCode;
    }

    // Getters and Setters
    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getDriverCode() {
        return driverCode;
    }

    public void setDriverCode(String driverCode) {
        this.driverCode = driverCode;
    }
}
