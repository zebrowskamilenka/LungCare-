package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import java.util.ArrayList;

public class VideoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_education);

        // Obsługa przycisku powrotu do panelu głównego
        View btnBack = findViewById(R.id.btnBackToPanel);
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }

        RecyclerView rv = findViewById(R.id.rvVideos);
        if (rv != null) {
            rv.setLayoutManager(new LinearLayoutManager(this));

            ArrayList<VideoItem> videos = new ArrayList<>();
            
            // Film 1: Wprowadzenie
            videos.add(new VideoItem(
                    "Pierwsze kroki w LungCare+",
                    "Zapoznanie z aplikacją i podstawowe zasady opieki po przeszczepie.",
                    "3:15",
                    R.drawable.thumb_placeholder,
                    R.raw.film_instruktazowy1
            ));

            // Film 2: Pielęgnacja
            videos.add(new VideoItem(
                    "Instruktaż pielęgnacji",
                    "Szczegółowy film dotyczący codziennej rutyny i higieny osobistej.",
                    "4:30",
                    R.drawable.thumb_placeholder,
                    R.raw.film_instruktazowy2
            ));

            // Film 3: Rehabilitacja (Dodany)
            videos.add(new VideoItem(
                    "Ćwiczenia oddechowe",
                    "Zestaw bezpiecznych ćwiczeń wspomagających wydolność płuc.",
                    "6:10",
                    R.drawable.thumb_placeholder,
                    R.raw.film_instruktazowy3
            ));

            VideoAdapter adapter = new VideoAdapter(videos, this::openPlayer);
            rv.setAdapter(adapter);
        }
    }

    private void openPlayer(VideoItem item) {
        Intent i = new Intent(this, VideoPlayerActivity.class);
        i.putExtra(VideoPlayerActivity.EXTRA_RAW_RES_ID, item.videoRawResId);
        startActivity(i);
    }
}
