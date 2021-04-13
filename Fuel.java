package com.example.localdatabase;

public class Fuel {

    // Attributes
    String fuelType;
    double fuelAmount;

    // Constructor Empty
    public Fuel() {

    }

    /**
     *
     * @param fuelType   Type of the fuel
     * @param fuelAmount Amount of fuel needed
     */
    public Fuel(String fuelType, double fuelAmount) {
        this.fuelType = fuelType;
        this.fuelAmount = fuelAmount;
    }

    // Getters and Setters
    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public double getFuelAmount() {
        return fuelAmount;
    }

    public void setFuelAmount(double fuelAmount) {
        this.fuelAmount = fuelAmount;
    }
}

