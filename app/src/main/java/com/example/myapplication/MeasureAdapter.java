package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MeasureAdapter extends RecyclerView.Adapter<MeasureAdapter.VH> {

    public interface OnItemClick {
        void onClick(MeasureEntry item);
    }

    private final List<MeasureEntry> items;
    private final OnItemClick onItemClick;

    public MeasureAdapter(List<MeasureEntry> items, OnItemClick onItemClick) {
        this.items = items;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_measure, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        MeasureEntry m = items.get(position);
        h.tvName.setText(m.name);
        h.tvDate.setText(m.dateTime);
        h.tvValue.setText(m.value);

        h.itemView.setOnClickListener(v -> {
            if (onItemClick != null) onItemClick.onClick(m);
        });
    }

    @Override
    public int getItemCount() { return items.size(); }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvName, tvDate, tvValue;

        VH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvValue = itemView.findViewById(R.id.tvValue);
        }
    }
}
