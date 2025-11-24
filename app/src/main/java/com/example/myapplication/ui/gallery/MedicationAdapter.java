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

    private ArrayList<Medication> medications;

    public MedicationAdapter(ArrayList<Medication> medications) {
        this.medications = medications;
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
        holder.tvName.setText(medications.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return medications.size();
    }

    static class MedicationViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;

        public MedicationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvMedicationName);
        }
    }
}