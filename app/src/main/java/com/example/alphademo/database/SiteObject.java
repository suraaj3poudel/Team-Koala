package com.example.alphademo.database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SiteObject {

    private String site;
    private String siteCode;
    private String siteAddress;
    private String siteCity;
    private String siteState;
    private String siteZIP;
    private String siteProduct;
    private String siteProductDesc;
    private JSONObject siteDetails;

    public SiteObject(JSONObject siteInfo) throws JSONException {
        siteDetails = siteInfo;
        sortData();
    }

    private void sortData() throws JSONException {


        String site2name = siteDetails.getString("DestinationName");
        site = site2name;

        String site2containercode = siteDetails.getString("SiteContainerCode");
        siteCode = site2containercode;

        String site2address = siteDetails.getString("Address1");
        siteAddress = site2address;

        String site2city = siteDetails.getString("City");
        siteCity = site2city;

        String site2state= siteDetails.getString("StateAbbrev");
        siteState= site2state;

        String site2zip = siteDetails.getString("PostalCode");
        siteZIP = site2zip;

        String site2p= siteDetails.getString("ProductCode");
        siteProduct= site2p;

        String site2pd = siteDetails.getString("ProductDesc");
        siteProductDesc = site2pd;

    }
    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public String getSiteAddress() {
        return siteAddress;
    }

    public void setSiteAddress(String siteAddress) {
        this.siteAddress = siteAddress;
    }

    public String getSiteCity() {
        return siteCity;
    }

    public void setSiteCity(String siteCity) {
        this.siteCity = siteCity;
    }

    public String getSiteState() {
        return siteState;
    }

    public void setSiteState(String siteState) {
        this.siteState = siteState;
    }

    public String getSiteZIP() {
        return siteZIP;
    }

    public void setSiteZIP(String siteZIP) {
        this.siteZIP = siteZIP;
    }

    public String getSiteProduct() {
        return siteProduct;
    }

    public void setSiteProduct(String siteProduct) {
        this.siteProduct = siteProduct;
    }

    public String getSiteProductDesc() {
        return siteProductDesc;
    }

    public void setSiteProductDesc(String siteProductDesc) {
        this.siteProductDesc = siteProductDesc;
    }


}
