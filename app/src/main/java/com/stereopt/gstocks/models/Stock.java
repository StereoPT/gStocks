package com.stereopt.gstocks.models;

public class Stock {
    private String name;

    public Stock() { }

    public Stock(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
