package com.example.localdatabase;

public class Truck {

    // Attributes
    String truckID;
    String truckCode;
    String truckDesc;

    // Constructor Empty
    public Truck() {

    }

    /**
     *
     * @param truckID   ID of the truck
     * @param truckCode Code of the truck
     * @param truckDesc Description of the truck
     */
    public Truck(String truckID, String truckCode, String truckDesc) {
        this.truckID = truckID;
        this.truckCode = truckCode;
        this.truckDesc = truckDesc;
    }

    // Getters and Setters
    public String getTruckID() {
        return truckID;
    }

    public void setTruckID(String truckID) {
        this.truckID = truckID;
    }

    public String getTruckCode() {
        return truckCode;
    }

    public void setTruckCode(String truckCode) {
        this.truckCode = truckCode;
    }

    public String getTruckDesc() {
        return truckDesc;
    }

    public void setTruckDesc(String truckDesc) {
        this.truckDesc = truckDesc;
    }
}
