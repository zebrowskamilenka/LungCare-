package com.example.myapplication.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class EducationFragment extends Fragment {

    private RecyclerView recyclerView;
    private VideoAdapter adapter;
    private ArrayList<Video> videos;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_education, container, false);

        recyclerView = root.findViewById(R.id.recyclerVideos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        videos = new ArrayList<>();

        // ▼ Tu dodajesz filmiki od lekarzy ▼
        videos.add(new Video("Jak dbać o przeszczepione płuca", "https://www.youtube.com/watch?v=xxxx"));
        videos.add(new Video("Ćwiczenia oddechowe po operacji", "https://www.youtube.com/watch?v=xxxx"));
        videos.add(new Video("Jak stosować leki immunosupresyjne", "https://www.youtube.com/watch?v=xxxx"));

        adapter = new VideoAdapter(videos, video -> {
            // Po kliknięciu
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(video.getUrl()));
            startActivity(intent);
        });

        recyclerView.setAdapter(adapter);

        return root;
    }
}
