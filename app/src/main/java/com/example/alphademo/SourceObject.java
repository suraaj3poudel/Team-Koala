package com.example.alphademo;

import org.json.JSONArray;
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
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getSourceAddress() {
        return sourceAddress;
    }

    public void setSourceAddress(String sourceAddress) {
        this.sourceAddress = sourceAddress;
    }

    public String getSourceCity() {
        return sourceCity;
    }

    public void setSourceCity(String sourceCity) {
        this.sourceCity = sourceCity;
    }

    public String getSourceState() {
        return sourceState;
    }

    public void setSourceState(String sourceState) {
        this.sourceState = sourceState;
    }

    public String getSourceZIP() {
        return sourceZIP;
    }

    public void setSourceZIP(String sourceZIP) {
        this.sourceZIP = sourceZIP;
    }


}
