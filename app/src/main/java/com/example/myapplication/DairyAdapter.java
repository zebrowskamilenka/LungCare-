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

        private ArrayList<DairyEntry> entries;

        public DairyAdapter(ArrayList<DairyEntry> entries) {
            this.entries = entries;
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
            holder.date.setText(entry.getDate());
            holder.text.setText(entry.getText());
        }

        @Override
        public int getItemCount() {
            return entries.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            TextView date, text;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                date = itemView.findViewById(R.id.tvDate);
                text = itemView.findViewById(R.id.tvText);
            }
        }
}
