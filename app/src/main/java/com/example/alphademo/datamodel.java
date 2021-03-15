package com.example.alphademo;

public class datamodel {
    int image;
    String header;

    public datamodel(int image, String header) {
        this.image = image;
        this.header = header;
    }

    public int getImage() {
        return image;
    }

    public String getHeader() {
        return header;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setHeader(String header) {
        this.header = header;
    }
}

