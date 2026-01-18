package com.example.myapplication;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.ArrayList;


    public class DairyAdapter extends RecyclerView.Adapter<DairyAdapter.ViewHolder> {

        private  final ArrayList<DairyEntry> entries;

        public DairyAdapter(ArrayList<DairyEntry> entries) {
            this.entries = entries;
        }
        static class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvDate, tvText;
            ViewHolder(View itemView) {
                super(itemView);
                tvDate = itemView.findViewById(R.id.tvDate);
                tvText = itemView.findViewById(R.id.tvText);
            }
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_dairy, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            DairyEntry entry = entries.get(position);
            holder.tvDate.setText(entry.date);
            holder.tvText.setText(
                    entry.mood + "\n" +
                         entry.symptoms + "\n" +
                    entry.meds
            );
        }

        @Override
        public int getItemCount() {
            return entries.size();
        }

        }

