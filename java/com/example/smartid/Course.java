package com.example.smartid;

public class Course {
    private String c_name;
    private String section;


    public Course(String c_name, String section) {
        this.c_name = c_name;
        this.section = section;
    }

    public String getC_name() {
        return c_name;
    }

    public String getSection() {
        return section;
    }
}
