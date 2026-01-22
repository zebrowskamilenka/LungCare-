package com.example.myapplication;

public class DayTask {
    public enum Type { MEDICATION, INHALATION, MEASUREMENT, VISIT, DIARY }

    public String time;     // "08:00"
    public String title;
    public String subtitle;

    public Type type;
    public boolean done;

    public DayTask(Type type, String title, String subtitle, String time, boolean completed) {
        this.time = time;
        this.title = title;
        this.subtitle = subtitle;
        this.type = type;
        this.done = done;
    }
}
