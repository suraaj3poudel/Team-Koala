package com.example.localdatabase;

public class Site {

    // Attributes
    String site = "";
    String siteCode = "";
    String siteAddress = "";
    String siteCity = "";
    String siteState = "";
    String siteZIP = "";

    // Constructor Empty
    public Site() {

    }

    // Constructors
    public Site(String site, String siteCode, String siteAddress, String siteCity, String siteState, String siteZIP) {
        this.site = site;
        this.siteCode = siteCode;
        this.siteAddress = siteAddress;
        this.siteCity = siteCity;
        this.siteState = siteState;
        this.siteZIP = siteZIP;
    }

    // Getters and Setters
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
}
