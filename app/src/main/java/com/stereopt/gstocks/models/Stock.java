package com.stereopt.gstocks.models;

import android.provider.BaseColumns;

public class Stock {
    private String symbol;
    private String name;
    private String region;

    public Stock() { }

    public Stock(String symbol, String name, String region) {
        this.symbol = symbol;
        this.name = name;
        this.region = region;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) { this.region = region; }

    //***** ***** ***** ***** ***** STOCK DATABASE ***** ***** ***** ***** *****//

    public static class StockEntry implements BaseColumns {
        public static final String TABLE_NAME = "stocks";
        public static final String COLUMN_SYMBOL = "symbol";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_REGION = "region";
    }
}
