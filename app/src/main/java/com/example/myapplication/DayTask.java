package com.example.myapplication;

public class DayTask {
    public enum Type { MEDICATION, INHALATION, MEASUREMENT, VISIT, DIARY }

    public Type type;
    public String title;
    public String subtitle;
    public String time;     // "08:00"
    public boolean completed;

    public DayTask(Type type, String title, String subtitle, String time, boolean completed) {
        this.type = type;
        this.title = title;
        this.subtitle = subtitle;
        this.time = time;
        this.completed = completed;
    }
}
