package com.example.smartid;

public class Payment {

    String date,vendor,amount;

    public Payment(String date, String vendor, String amount) {
        this.date = date;
        this.vendor = vendor;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public String getVendor() {
        return vendor;
    }

    public String getAmount() {
        return amount;
    }
}
