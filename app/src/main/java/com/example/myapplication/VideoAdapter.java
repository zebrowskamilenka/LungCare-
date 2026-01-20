//package com.example.myapplication;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VH> {
//
//    public interface OnClick {
//        void onVideoClick(VideoItem item);
//    }
//
//    private final List<VideoItem> items;
//    private final OnClick onClick;
//
//    public VideoAdapter(List<VideoItem> items, OnClick onClick) {
//        this.items = items;
//        this.onClick = onClick;
//    }
//
//    @NonNull
//    @Override
//    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video, parent, false);
//        return new VH(v);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull VH h, int position) {
//        VideoItem item = items.get(position);
//        h.tvTitle.setText(item.title);
//        h.tvDesc.setText(item.description);
//
//        h.itemView.setOnClickListener(v -> {
//            if (onClick != null) onClick.onVideoClick(item);
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return items.size();
//    }
//
//    static class VH extends RecyclerView.ViewHolder {
//        TextView tvTitle, tvDesc;
//        VH(@NonNull View itemView) {
//            super(itemView);
//            tvTitle = itemView.findViewById(R.id.tvTitle);
//            tvDesc = itemView.findViewById(R.id.tvDesc);
//        }
//    }
//}
