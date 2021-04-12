package com.example.alphademo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TripInfoClass {

    private String driverName;
    private String driverCode;
    private String truckID;
    private String transport;
    private String tripId;
    private String tripName;

    private String source;
    private String sourceCode;
    private String sourceAddress;
    private String sourceCity;
    private String sourceState;
    private String sourceZIP;

    private String site;
    private String siteCode;
    private String siteAddress;
    private String siteCity;
    private String siteState;
    private String siteZIP;

    private String site2;
    private String site2Code;
    private String site2Address;
    private String site2City;
    private String site2State;
    private String site2Product;
    private String site2ProductDesc;

    public String getSite2Product() {
        return site2Product;
    }

    public String getSite2ProductDesc() {
        return site2ProductDesc;
    }




    public String getSite2() {
        return site2;
    }

    public String getSite2Code() {
        return site2Code;
    }

    public String getSite2Address() {
        return site2Address;
    }

    public String getSite2City() {
        return site2City;
    }

    public String getSite2State() {
        return site2State;
    }

    public String getSite2ZIP() {
        return site2ZIP;
    }


    private String site2ZIP;




    public String getTruckID() {
        return truckID;
    }

    public String getTransport() {
        return transport;
    }

    public String getTripId() {
        return tripId;
    }

    public String getTripName() {
        return tripName;
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

    public String getSite() {
        return site;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public String getSiteAddress() {
        return siteAddress;
    }

    public String getSiteCity() {
        return siteCity;
    }

    public String getSiteState() {
        return siteState;
    }

    public String getSiteZIP() {
        return siteZIP;
    }

    public JSONArray getDriverDetails() {
        return driverDetails;
    }

    public String getDriverCode() {
        return driverCode;
    }

    private JSONArray driverDetails;

    public TripInfoClass(){}

    public TripInfoClass(JSONArray driver) throws JSONException {
        driverDetails = driver;
        sortData();
    }

    private void sortData() throws JSONException {

        JSONObject driverInfo = driverDetails.getJSONObject(0);
        String driver = driverInfo.getString("DriverName");
        driverName = driver;

        String drivercode = driverInfo.getString("DriverCode");
        driverCode = drivercode;

        String truckid = driverInfo.getString("TruckId");
        truckID = truckid;

        String transports = driverInfo.getString("TruckDesc");
        transport = transports;

        String tripid = driverInfo.getString("TripId");
        tripId = tripid;

        String tripname = driverInfo.getString("TripName");
        tripName = tripname;

        String sourcename = driverInfo.getString("DestinationName");
        source = sourcename;

        String sourcecode = driverInfo.getString("DestinationCode");
        sourceCode = sourcecode;

        String sourceaddress = driverInfo.getString("Address1");
        sourceAddress = sourceaddress;

        String sourcecity = driverInfo.getString("City");
        sourceCity = sourcecity;

        String sourcestate= driverInfo.getString("StateAbbrev");
        sourceState= sourcestate;

        String sourcezip = driverInfo.getString("PostalCode");
        sourceZIP = sourcezip;



        JSONObject siteInfo = driverDetails.getJSONObject(1);

        String sitename = siteInfo.getString("DestinationName");
        site = sitename;

        String sitecontainercode = siteInfo.getString("SiteContainerCode");
        siteCode = sitecontainercode;

        String siteaddress = siteInfo.getString("Address1");
        siteAddress = siteaddress;

        String sitecity = siteInfo.getString("City");
        siteCity = sitecity;

        String sitestate= siteInfo.getString("StateAbbrev");
        siteState= sitestate;

        String sitezip = siteInfo.getString("PostalCode");
        siteZIP = sitezip;


        JSONObject site2Info = driverDetails.getJSONObject(2);

        String site2name = site2Info.getString("DestinationName");
        site2 = site2name;

        String site2containercode = site2Info.getString("SiteContainerCode");
        site2Code = site2containercode;

        String site2address = site2Info.getString("Address1");
        site2Address = site2address;

        String site2city = site2Info.getString("City");
        site2City = site2city;

        String site2state= site2Info.getString("StateAbbrev");
        site2State= site2state;

        String site2zip = site2Info.getString("PostalCode");
        site2ZIP = site2zip;

        String site2p= site2Info.getString("ProductCode");
        site2Product= site2p;

        String site2pd = site2Info.getString("ProductDesc");
        site2ProductDesc = site2pd;


    }

    public String getDriverName(){
        return driverName;
    }



}
