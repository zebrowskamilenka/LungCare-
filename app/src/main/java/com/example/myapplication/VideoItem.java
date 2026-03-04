package com.example.myapplication;

public class VideoItem {


    public final String title;
    public final String desc;
    public final String duration;
    public final int thumbResId;
    public final int videoRawResId; // <- MP4 w res/raw

    public VideoItem(String title, String desc, String duration, int thumbResId, int videoRawResId) {
        this.title = title;
        this.desc = desc;
        this.duration = duration;
        this.thumbResId = thumbResId;
        this.videoRawResId = videoRawResId;
    }
}
