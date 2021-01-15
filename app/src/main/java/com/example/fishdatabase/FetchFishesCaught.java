package com.example.fishdatabase;

import java.io.Serializable;

public class FetchFishesCaught implements Serializable {

    String name;
    String size;

    public FetchFishesCaught(String name, String size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
