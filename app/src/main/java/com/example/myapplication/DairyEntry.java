package com.example.myapplication;

public class DairyEntry {
    private String date;
    private String text;

    public DairyEntry(String date, String text) {
        this.date = date;
        this.text = text;
    }

    public String getDate() { return date; }
    public String getText() { return text; }
}
