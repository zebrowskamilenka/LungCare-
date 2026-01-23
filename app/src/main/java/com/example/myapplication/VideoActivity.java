package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class VideoActivity extends AppCompatActivity {
//
//    public static final String EXTRA_URL = "extra_url";
//    public static final String EXTRA_TITLE = "extra_title";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_education);
//
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setTitle("Wideo edukacyjne");
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        }
//
//        RecyclerView rv = findViewById(R.id.rvVideos);
//        rv.setLayoutManager(new LinearLayoutManager(this));
//
//        List<VideoItem> list = new ArrayList<>();
//        // Na start możesz dać testowe linki MP4 (później podmienisz na swoje)
    //    list.add(new VideoItem("Ćwiczenia oddechowe", "Krótki instruktaż do codziennej praktyki", "https://example.com/video1.mp4"));
//        list.add(new VideoItem("Inhalacja krok po kroku", "Jak poprawnie wykonać inhalację", "https://example.com/video2.mp4"));
//        list.add(new VideoItem("Higiena i pielęgnacja", "Zalecenia po przeszczepie", "https://example.com/video3.mp4"));
//
//        VideoAdapter adapter = new VideoAdapter(list, item -> {
//            Intent i = new Intent(VideoActivity.this, VideoPlayerActivity.class);
//            i.putExtra(EXTRA_URL, item.url);
//            i.putExtra(EXTRA_TITLE, item.title);
//            startActivity(i);
//        });
//
//        rv.setAdapter(adapter);
//    }
//
//    @Override
//    public boolean onSupportNavigateUp() {
//        finish();
//        return true;
//    }
//}