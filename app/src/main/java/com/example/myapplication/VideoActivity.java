package com.example.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import java.util.ArrayList;

public class VideoActivity extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_education, container, false);

        RecyclerView rv = root.findViewById(R.id.rvVideos);
        rv.setLayoutManager(new LinearLayoutManager(requireContext()));

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


        return root;
    }

    private void openPlayer(VideoItem item) {
        Intent i = new Intent(requireContext(), VideoPlayerActivity.class);
        i.putExtra(VideoPlayerActivity.EXTRA_RAW_RES_ID, item.videoRawResId);
        startActivity(i);

    }
}