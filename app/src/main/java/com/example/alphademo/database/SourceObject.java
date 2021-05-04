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
    private  String fuelType;

    public String getSourceProduct() {
        return sourceProduct;
    }

    private String sourceProduct;
    private JSONObject sourceDetails;
    private String pid;

    public String getPid() {
        return pid;
    }

    public String getQuantity() {
        return quantity;
    }

    private String quantity;

    private String fillInfo;
    private String scc;

    public String getFillInfo() {
        return fillInfo;
    }

    public String getScc() {
        return scc;
    }

    public String getScd() {
        return scd;
    }

    public String getDrn() {
        return drn;
    }

    public String getDelReqlineNum() {
        return delReqlineNum;
    }

    private  String scd;
    private String drn;
    private String delReqlineNum;

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

        String site2p= sourceDetails.getString("ProductCode");
        sourceProduct= site2p;

        double sourcelat = sourceDetails.getDouble("Latitude");
        latitude = sourcelat;

        double sourcelong = sourceDetails.getDouble("Longitude");
        longitude = sourcelong;

        String fueltype = sourceDetails.getString("ProductDesc");
        fuelType = fueltype;

        String siCC = sourceDetails.getString("SiteContainerCode");
        scc = siCC;

        String siCD = sourceDetails.getString("SiteContainerDescription");
        scd = siCD;

        String deRN = sourceDetails.getString("DelReqNum");
        drn = deRN;

        String deRLN = sourceDetails.getString("DelReqLineNum");
        delReqlineNum = deRLN;

        String pID = sourceDetails.getString("ProductId");
        pid = pID;

        String fiLL= sourceDetails.getString("Fill");
        fillInfo = fiLL;

        String qTY= sourceDetails.getString("RequestedQty");
        quantity = qTY;
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


    public String getFuelType() {

        return fuelType;
    }

}
