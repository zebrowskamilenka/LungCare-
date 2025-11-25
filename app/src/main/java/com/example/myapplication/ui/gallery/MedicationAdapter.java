package com.example.myapplication.ui.gallery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.MedicationViewHolder> {

    private ArrayList<Medication> list;

    public MedicationAdapter(ArrayList<Medication> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MedicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_medication, parent, false);
        return new MedicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationViewHolder holder, int position) {
        Medication med = list.get(position);

        holder.tvName.setText(med.getName());
        holder.tvDose.setText(med.getDose());
        holder.tvTime.setText(med.getTime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MedicationViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvDose, tvTime;

        public MedicationViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvMedicationName);
            tvDose = itemView.findViewById(R.id.tvMedicationDose);
            tvTime = itemView.findViewById(R.id.tvMedicationTime);
        }
    }
}
