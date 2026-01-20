package com.example.myapplication;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class DayTaskAdapter extends RecyclerView.Adapter<DayTaskAdapter.VH>{
    public interface Listener {
        void onTaskChecked(DayTask task, boolean checked);
        void onTaskAction(DayTask task);
    }

    private final List<DayTask> items = new ArrayList<>();
    private final Listener listener;

    public DayTaskAdapter(Listener listener) {
        this.listener = listener;
    }

    public void setItems(List<DayTask> newItems) {
        items.clear();
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_day_task, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {
        DayTask task = items.get(position);

        h.tvTitle.setText(task.title);
        h.tvSubtitle.setText(task.subtitle == null ? "" : task.subtitle);
        h.tvTime.setText(task.time == null ? "" : task.time);

        // Ikonka typu (emoji – najprościej na start)
        h.tvTypeIcon.setText(typeToEmoji(task.type));

        // Status bar (na start prosto: szary/zielony)
        h.viewStatus.setBackgroundColor(
                task.completed ? 0xFF9E9E9E : 0xFFBDBDBD
        );

        // Checkbox: dla wizyty zwykle nie ma "wykonane" (albo możesz mieć)
        boolean showCheckbox = task.type != DayTask.Type.VISIT;
        h.cbDone.setVisibility(showCheckbox ? View.VISIBLE : View.GONE);

        // Przycisk akcji: dla wizyty (albo innych typów)
        boolean showAction = task.type == DayTask.Type.VISIT;
        h.btnAction.setVisibility(showAction ? View.VISIBLE : View.GONE);

        // Uwaga: żeby nie robić podwójnych eventów
        h.cbDone.setOnCheckedChangeListener(null);
        h.cbDone.setChecked(task.completed);

        h.cbDone.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.completed = isChecked;
            if (listener != null) listener.onTaskChecked(task, isChecked);
        });

        h.btnAction.setOnClickListener(v -> {
            if (listener != null) listener.onTaskAction(task);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        View viewStatus;
        TextView tvTypeIcon, tvTitle, tvSubtitle, tvTime;
        CheckBox cbDone;
        MaterialButton btnAction;

        VH(@NonNull View itemView) {
            super(itemView);
            viewStatus = itemView.findViewById(R.id.viewStatus);
            tvTypeIcon = itemView.findViewById(R.id.tvTypeIcon);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvSubtitle = itemView.findViewById(R.id.tvSubtitle);
            tvTime = itemView.findViewById(R.id.tvTime);
            cbDone = itemView.findViewById(R.id.cbDone);
            btnAction = itemView.findViewById(R.id.btnAction);
        }
    }

    private String typeToEmoji(DayTask.Type type) {
        switch (type) {
            case MEDICATION: return "💊";
            case INHALATION: return "🌫";
            case MEASUREMENT: return "🧪";
            case VISIT: return "📅";
            case DIARY: return "📝";
            default: return "•";
        }
    }
}
