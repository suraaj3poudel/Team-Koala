package com.example.localdatabase;

public class Trip {

    // Attributes
    String tripId = "";
    String tripName = "";

    // Constructor Empty
    public Trip() {

    }

    // Constructor
    public Trip(String tripId, String tripName) {
        this.tripId = tripId;
        this.tripName = tripName;
    }

    // Getters and Setters
    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }
}
