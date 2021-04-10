package com.example.localdatabase;

public class Source {

    // Attributes
    String source = "";
    String sourceCode = "";
    String sourceAddress = "";
    String sourceCity = "";
    String sourceState = "";
    String sourceZIP = "";

    // Constructor Empty
    public Source() {

    }

    // Constructors
    public Source(String source, String sourceCode, String sourceAddress, String sourceCity, String sourceState,
                  String sourceZIP) {
        this.source = source;
        this.sourceCode = sourceCode;
        this.sourceAddress = sourceAddress;
        this.sourceCity = sourceCity;
        this.sourceState = sourceState;
        this.sourceZIP = sourceZIP;
    }

    // Getters and Setters
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
