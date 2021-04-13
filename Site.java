package com.example.localdatabase;

public class Site {

    // Attributes

    String siteCode;
    String siteName;
    String siteAddress;
    String siteCity;
    String siteState;
    String siteZIP;

    // Constructor Empty
    public Site() {

    }

    /**
     *
     * @param siteCode Code of the site
     * @param siteName Name of the site
     * @param siteAddress Address of the site
     * @param siteCity City of the site
     * @param siteState State of the site
     * @param siteZIP ZIP code of the site
     */
    public Site(String siteName, String siteCode, String siteAddress, String siteCity, String siteState, String siteZIP) {
        this.siteName = siteName;
        this.siteCode = siteCode;
        this.siteAddress = siteAddress;
        this.siteCity = siteCity;
        this.siteState = siteState;
        this.siteZIP = siteZIP;
    }

    //Getters and Setters
    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
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
