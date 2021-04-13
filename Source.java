package com.example.localdatabase;

public class Source {

    // Attributes

    String sourceCode;
    String sourceName;
    String sourceAddress;
    String sourceCity;
    String sourceState;
    String sourceZIP;

    // Constructor Empty
    public Source() {

    }

    /**
     *
     * @param sourceName    Name of the source
     * @param sourceCode    Code of the source
     * @param sourceAddress Address of the source
     * @param sourceCity    City of the source
     * @param sourceState   State of the source
     * @param sourceZIP     ZIP of the source
     */
    public Source(String sourceName, String sourceCode, String sourceAddress, String sourceCity, String sourceState,
                  String sourceZIP) {
        this.sourceName = sourceName;
        this.sourceCode = sourceCode;
        this.sourceAddress = sourceAddress;
        this.sourceCity = sourceCity;
        this.sourceState = sourceState;
        this.sourceZIP = sourceZIP;
    }

    // Getters and Setters
    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
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
