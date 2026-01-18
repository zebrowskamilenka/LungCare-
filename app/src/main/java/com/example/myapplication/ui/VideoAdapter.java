package com.example.myapplication.ui;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.List;

public class VideoAdapter extends ArrayAdapter<Video> {

    public VideoAdapter(Context context, List<Video> videos) {
        super(context, 0, videos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.video_list_item, parent, false);
        }

        Video video = getItem(position);

        TextView title = convertView.findViewById(R.id.titleText);
        TextView subtitle = convertView.findViewById(R.id.subText);

        if (video != null) {
            title.setText(video.getTitle());
            subtitle.setText(video.getDescription());
        }

        return convertView;
    }
}

