package com.example.smartid;

public class Attendance {

    String date,present,absent;

    public Attendance(String date, String present, String absent) {
        this.date = date;
        this.present = present;
        this.absent = absent;
    }

    public String getDate() {
        return date;
    }

    public String getPresent() {
        return present;
    }

    public String getAbsent() {
        return absent;
    }
}
