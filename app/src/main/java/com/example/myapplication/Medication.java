package com.example.myapplication;

public class Medication {
    private final String name;
    private final String dose;
    private final String time;

    public Medication(String name, String dose, String time) {
        this.name = name;
        this.dose = dose;
        this.time = time;
    }

    public String getName() { return name; }
    public String getDose() { return dose; }
    public String getTime() { return time; }
}
