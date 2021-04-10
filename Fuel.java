package com.example.localdatabase;

public class Fuel {

    // Attributes
    String fuelName = "";
    double fuelAmount = 0.0;

    // Constructor Empty
    public Fuel() {

    }

    // Constructors
    public Fuel(String fuelName, double fuelAmount) {
        this.fuelName = fuelName;
        this.fuelAmount = fuelAmount;
    }

    // Getters and Setters
    public String getFuelName() {
        return fuelName;
    }

    public void setFuelName(String fuelName) {
        this.fuelName = fuelName;
    }

    public double getFuelAmount() {
        return fuelAmount;
    }

    public void setFuelAmount(double fuelAmount) {
        this.fuelAmount = fuelAmount;
    }
}
