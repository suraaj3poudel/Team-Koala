package com.example.alphademo.database;

public class TripInfo {
    private String id;
    private int numberSources;
    private int numberSites;


    public String getId() {
        return id;
    }

    public int getNumberSources() {
        return numberSources;
    }

    public int getNumberSites() {
        return numberSites;
    }


    public TripInfo(String id, int numberSources, int numberSites) {
        this.id = id;
        this.numberSources = numberSources;
        this.numberSites = numberSites;
    }



}
