package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DayTaskAdapter extends RecyclerView.Adapter<DayTaskAdapter.VH> {

    public interface OnDoneChanged {
        void onChanged(int position, boolean done);
    }

    private final List<DayTask> items;
    private final OnDoneChanged onDoneChanged;

    public DayTaskAdapter(List<DayTask> items, OnDoneChanged onDoneChanged) {
        this.items = items;
        this.onDoneChanged = onDoneChanged;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_day_task, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        DayTask t = items.get(position);

        h.tvTitle.setText(t.time + "  " + t.title);
        h.tvSub.setText(t.subtitle);

        h.cbDone.setOnCheckedChangeListener(null);
        h.cbDone.setChecked(t.done);

        h.cbDone.setOnCheckedChangeListener((buttonView, isChecked) -> {
            t.done = isChecked;
            if (onDoneChanged != null) onDoneChanged.onChanged(position, isChecked);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvTitle, tvSub;
        CheckBox cbDone;

        VH(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSub = itemView.findViewById(R.id.tvSubtitle);
            cbDone = itemView.findViewById(R.id.cbDone);
        }
    }
}
