package com.example.localdatabase;

public class Truck {

    // Attributes
    String truckID = "";
    String transport = "";

    // Constructor Empty
    public Truck() {

    }

    // Constructors
    public Truck(String truckID, String transport) {
        this.truckID = truckID;
        this.transport = transport;
    }

    // Getters and Setters
    public String getTruckID() {
        return truckID;
    }

    public void setTruckID(String truckID) {
        this.truckID = truckID;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }
}
