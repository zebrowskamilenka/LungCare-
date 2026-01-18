package com.example.myapplication.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;

import java.util.ArrayList;

public class EducationFragment extends Fragment {

    private ArrayList<Video> videos;
    private VideoAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        // Tutaj podajemy layout fragmentu
        return inflater.inflate(R.layout.fragment_education, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Znajdź ListView z layoutu fragmentu
        ListView listView = view.findViewById(R.id.videoList);

        // Przygotuj listę filmów
        videos = new ArrayList<>();
        videos.add(new Video(
                "Ćwiczenie oddechowe 1",
                "Oddychanie przeponowe",
                "https://www.youtube.com/watch?v=dQw4w9WgXcQ"  // tu wstaw swój link
        ));
        videos.add(new Video(
                "Instrukcja inhalacji",
                "Jak prawidłowo używać inhalatora",
                "https://example.com/inhalacja"
        ));
        videos.add(new Video(
                "Technika PEP",
                "Ćwiczenia ułatwiające wydech",
                "https://example.com/pep"
        ));
        videos.add(new Video(
                "Pozycje drenażowe",
                "Oczyszczanie dróg oddechowych",
                "https://example.com/drenaz"
        ));
        VideoAdapter adapter = new VideoAdapter(requireContext(), videos);
        listView.setAdapter(adapter);
        // Utwórz adapter i podłącz do ListView
        adapter = new VideoAdapter(requireContext(), videos);
        listView.setAdapter(adapter);

        // Obsługa kliknięcia na poszczególny film
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View itemView,
                                    int position,
                                    long id) {

                Video clickedVideo = videos.get(position);
                String url = clickedVideo.getUrl();

                if (url != null && !url.isEmpty()) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
            }
        });
    }
}
