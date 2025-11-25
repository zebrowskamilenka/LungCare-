package com.example.myapplication.ui.gallery;
public class Medication {
    private String name;
    private String dose;
    private String time;

    public Medication(String name, String dose, String time) {
        this.name = name;
        this.dose = dose;
        this.time = time;
    }

    public String getName() { return name; }
    public String getDose() { return dose; }
    public String getTime() { return time; }
}


