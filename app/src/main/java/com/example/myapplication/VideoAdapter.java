package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VH> {

    public interface OnVideoClickListener {
        void onClick(VideoItem item);
    }

    private final List<VideoItem> items;
    private final OnVideoClickListener listener;

    public VideoAdapter(List<VideoItem> items, OnVideoClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Zmieniono z R.layout.item_video na R.layout.video_list_item
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list_item, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        VideoItem item = items.get(position);

        h.tvTitle.setText(item.title);
        h.tvDesc.setText(item.desc);
        h.tvDuration.setText(item.duration);
        h.ivThumb.setImageResource(item.thumbResId);

        // Kliknięcie w całą kartę otwiera film
        h.itemView.setOnClickListener(v -> listener.onClick(item));
        
        // Kliknięcie w przycisk "Oglądaj" również otwiera film
        if (h.btnWatch != null) {
            h.btnWatch.setOnClickListener(v -> listener.onClick(item));
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView ivThumb;
        TextView tvTitle, tvDesc, tvDuration;
        MaterialButton btnWatch;

        VH(@NonNull View itemView) {
            super(itemView);
            ivThumb = itemView.findViewById(R.id.ivThumbnail); // Poprawione ID
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDesc = itemView.findViewById(R.id.tvDesc);
            tvDuration = itemView.findViewById(R.id.tvDuration);
            btnWatch = itemView.findViewById(R.id.btnWatch); // Dodane ID przycisku
        }
    }
}
