package com.example.alphademo.database;

import org.json.JSONException;
import org.json.JSONObject;

public class SourceObject {

    private String source;
    private String sourceCode;
    private String sourceAddress;
    private String sourceCity;
    private String sourceState;
    private String sourceZIP;
    private JSONObject sourceDetails;

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    private double latitude;
    private double longitude;

    public SourceObject(JSONObject sourceInfo) throws JSONException {
        sourceDetails = sourceInfo;
        sortData();
    }

    private void sortData() throws JSONException {

        String sourcename = sourceDetails.getString("DestinationName");
        source = sourcename;

        String sourcecode = sourceDetails.getString("DestinationCode");
        sourceCode = sourcecode;

        String sourceaddress = sourceDetails.getString("Address1");
        sourceAddress = sourceaddress;

        String sourcecity = sourceDetails.getString("City");
        sourceCity = sourcecity;

        String sourcestate= sourceDetails.getString("StateAbbrev");
        sourceState= sourcestate;

        String sourcezip = sourceDetails.getString("PostalCode");
        sourceZIP = sourcezip;

        double sourcelat = sourceDetails.getDouble("Latitude");
        latitude = sourcelat;

        double sourcelong = sourceDetails.getDouble("Longitude");
        longitude = sourcelong;
    }

    public String getSource() {
        return source;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public String getSourceCity() {
        return sourceCity;
    }

    public String getSourceState() {
        return sourceState;
    }

    public String getSourceZIP() {
        return sourceZIP;
    }



}
