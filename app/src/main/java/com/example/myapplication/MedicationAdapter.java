package com.example.myapplication;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.VH> {

    private final List<Medication> all;
    private final List<Medication> visible;

    public MedicationAdapter(List<Medication> data) {
        all = new ArrayList<>(data);
        visible = new ArrayList<>(data);
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_medication, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Medication m = visible.get(position);

        holder.tvName.setText(m.getName());
        holder.tvDose.setText("Dawka: " + m.getDose());
        holder.tvTime.setText("Godzina: " + m.getTime());

        // ✅ KLIK W LEK → SZCZEGÓŁY
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), MedicationDetailsActivity.class);
            intent.putExtra("name", m.getName());
            intent.putExtra("dose", m.getDose());
            intent.putExtra("time", m.getTime());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return visible.size();
    }

    public void filter(String query) {
        String q = query == null ? "" : query.trim().toLowerCase(Locale.ROOT);
        visible.clear();

        if (q.isEmpty()) {
            visible.addAll(all);
        } else {
            for (Medication m : all) {
                if (m.getName().toLowerCase(Locale.ROOT).contains(q)) {
                    visible.add(m);
                }
            }
        }
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder {
        TextView tvName, tvDose, tvTime;

        VH(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDose = itemView.findViewById(R.id.tvDose);
            tvTime = itemView.findViewById(R.id.tvTime);
        }
    }
}