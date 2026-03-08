package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_education); // Używamy tego samego layoutu co wcześniej

        RecyclerView rv = findViewById(R.id.rvVideos);
        rv.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<VideoItem> videos = new ArrayList<>();
        videos.add(new VideoItem(
                "Codzienna pielęgnacja pacjenta",
                "Codzienne zasady higieny i opieki po przeszczepie płuc.",
                "4:27",
                R.drawable.thumb_placeholder,
                R.raw.inhalacja
        ));
        videos.add(new VideoItem(
                "Jak poprawnie stosować inhalator?",
                "Instrukcja stosowania inhalatora ze spejserem.",
                "3:12",
                R.drawable.thumb_placeholder,
                R.raw.inhalacja
        ));
        videos.add(new VideoItem(
                "Przyjmowanie leków immunosupresyjnych",
                "Jak regularnie przyjmować leki i czego unikać.",
                "5:04",
                R.drawable.thumb_placeholder,
                R.raw.inhalacja
        ));
        videos.add(new VideoItem(
                "Higiena i unikanie infekcji",
                "Jak ograniczać ryzyko infekcji po przeszczepie.",
                "3:38",
                R.drawable.thumb_placeholder,
                R.raw.inhalacja
        ));

        VideoAdapter adapter = new VideoAdapter(videos, this::openPlayer);
        rv.setAdapter(adapter);
    }

    private void openPlayer(VideoItem item) {
        Intent i = new Intent(this, VideoPlayerActivity.class);
        i.putExtra(VideoPlayerActivity.EXTRA_RAW_RES_ID, item.videoRawResId);
        startActivity(i);
    }
}
