package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DiaryActivity extends AppCompatActivity {

    // prefs
    private SharedPreferences prefs;
    private static final String PREFS_NAME = "dairy";
    private static final String KEY_ENTRIES = "entries_json";
    private static final String KEY_BASELINE_FEV1 = "baseline_fev1";
    private static final float DEFAULT_BASELINE_FEV1 = 2.5f;

    // UI
    private TextView tvDate;
    private TextInputEditText etFev1, etSpo2, etTemp, etHr, etOtherSymptoms, etMeds;
    private ChipGroup chipGroupMood, chipGroupSymptoms;
    private MaterialButton btnSave;

    private long selectedDateUtcMillis = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        bindViews();
        initDefaultDate();
        initDatePicker();
        initSave();
    }

    private void bindViews() {
        tvDate = findViewById(R.id.tvDate);

        etFev1 = findViewById(R.id.etFev1);
        etSpo2 = findViewById(R.id.etSpo2);
        etTemp = findViewById(R.id.etTemp);
        etHr   = findViewById(R.id.etHr);

        chipGroupMood = findViewById(R.id.chipGroupMood);
        chipGroupSymptoms = findViewById(R.id.chipGroupSymptoms);

        etOtherSymptoms = findViewById(R.id.etOtherSymptoms);
        // etMeds = findViewById(R.id.etMeds); // Jeśli masz to pole w layout

        btnSave = findViewById(R.id.btnSave);
    }

    private void initDefaultDate() {
        selectedDateUtcMillis = MaterialDatePicker.todayInUtcMilliseconds();
        tvDate.setText(formatDatePL(selectedDateUtcMillis));
    }

    private void initDatePicker() {
        tvDate.setOnClickListener(v -> {
            MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Wybierz datę")
                    .setSelection(selectedDateUtcMillis)
                    .build();

            picker.addOnPositiveButtonClickListener(selection -> {
                if (selection != null) {
                    selectedDateUtcMillis = selection;
                    tvDate.setText(formatDatePL(selection));
                }
            });

            picker.show(getSupportFragmentManager(), "DATE_PICKER");
        });
    }

    private void initSave() {
        btnSave.setOnClickListener(v -> {
            JSONObject entry = collectEntryAsJson();
            if (entry == null) return;

            ClinicalFlags flags = evaluateFlags(entry);

            if (!appendEntry(entry, flags)) {
                toast("Nie udało się zapisać wpisu.");
                return;
            }

            showResultDialog(flags, entry);
            clearInputs();
        });
    }

    @Nullable
    private JSONObject collectEntryAsJson() {
        String dateLabel = tvDate.getText().toString().trim();
        if (TextUtils.isEmpty(dateLabel) || dateLabel.equalsIgnoreCase("Wybierz datę")) {
            toast("Wybierz datę.");
            return null;
        }

        int checkedMoodId = chipGroupMood.getCheckedChipId();
        if (checkedMoodId == View.NO_ID) {
            toast("Wybierz samopoczucie.");
            return null;
        }
        Chip moodChip = findViewById(checkedMoodId);
        String mood = moodChip.getText().toString();

        Float fev1 = parseFloatOrNull(etFev1);
        Integer spo2 = parseIntOrNull(etSpo2);
        Float temp = parseFloatOrNull(etTemp);
        Integer hr = parseIntOrNull(etHr);

        String symptoms = collectSymptoms();
        String meds = safeText(etMeds);

        boolean anyParam = (fev1 != null || spo2 != null || temp != null || hr != null);
        boolean anyText = !TextUtils.isEmpty(symptoms) || !TextUtils.isEmpty(meds);
        if (!anyParam && !anyText) {
            toast("Uzupełnij przynajmniej parametry lub objawy.");
            return null;
        }

        JSONObject obj = new JSONObject();
        try {
            obj.put("dateUtcMillis", selectedDateUtcMillis);
            obj.put("dateLabel", dateLabel);
            obj.put("fev1", fev1 != null ? fev1 : JSONObject.NULL);
            obj.put("spo2", spo2 != null ? spo2 : JSONObject.NULL);
            obj.put("temp", temp != null ? temp : JSONObject.NULL);
            obj.put("hr", hr != null ? hr : JSONObject.NULL);
            obj.put("mood", mood);
            obj.put("symptoms", symptoms);
            obj.put("meds", meds);
        } catch (JSONException e) {
            e.printStackTrace();
            toast("Błąd danych.");
            return null;
        }

        return obj;
    }

    private String collectSymptoms() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chipGroupSymptoms.getChildCount(); i++) {
            View child = chipGroupSymptoms.getChildAt(i);
            if (child instanceof Chip) {
                Chip c = (Chip) child;
                if (c.isChecked()) {
                    if (sb.length() > 0) sb.append(", ");
                    sb.append(c.getText());
                }
            }
        }
        String other = safeText(etOtherSymptoms);
        if (!TextUtils.isEmpty(other)) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(other);
        }
        return sb.toString();
    }

    private static class ClinicalFlags {
        boolean red;
        boolean yellow;
        String message;
    }

    private ClinicalFlags evaluateFlags(JSONObject entry) {
        ClinicalFlags f = new ClinicalFlags();

        Float fev1 = jsonFloat(entry, "fev1");
        Integer spo2 = jsonInt(entry, "spo2");
        Float temp = jsonFloat(entry, "temp");
        String symptoms = entry.optString("symptoms", "");

        float baseline = loadBaselineFev1();
        boolean fever = (temp != null && temp >= 38.0f);
        boolean lowSpo2 = (spo2 != null && spo2 <= 92);

        boolean fev1Drop = false;
        if (fev1 != null && baseline > 0f) {
            float dropPct = (baseline - fev1) / baseline * 100f;
            fev1Drop = dropPct >= 10.0f;
        }

        boolean dyspnea = symptoms.toLowerCase(Locale.ROOT).contains("duszność");

        if (fever || lowSpo2 || (fev1Drop && dyspnea)) {
            f.red = true;
        } else if (fev1Drop) {
            f.yellow = true;
        }

        if (f.red) {
            f.message = "Uwaga: parametry/objawy mogą wymagać kontaktu z ośrodkiem transplantacyjnym.";
        } else if (f.yellow) {
            f.message = "Ostrzeżenie: możliwy spadek FEV1 względem wartości wyjściowej. Rozważ powtórzenie pomiaru.";
        } else {
            f.message = "Wpis zapisany. Na podstawie podanych danych brak alarmów.";
        }

        return f;
    }

    private float loadBaselineFev1() {
        String s = prefs.getString(KEY_BASELINE_FEV1, String.valueOf(DEFAULT_BASELINE_FEV1));
        try { return Float.parseFloat(s); } catch (Exception e) { return DEFAULT_BASELINE_FEV1; }
    }

    private boolean appendEntry(JSONObject entry, ClinicalFlags flags) {
        try {
            String existing = prefs.getString(KEY_ENTRIES, "[]");
            JSONArray arr = new JSONArray(existing);
            entry.put("flag_red", flags.red);
            entry.put("flag_yellow", flags.yellow);
            arr.put(entry);
            prefs.edit().putString(KEY_ENTRIES, arr.toString()).apply();
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showResultDialog(ClinicalFlags flags, JSONObject entry) {
        String details = buildDetails(entry);
        new MaterialAlertDialogBuilder(this)
                .setTitle(flags.red ? "Alert" : (flags.yellow ? "Ostrzeżenie" : "Zapisano"))
                .setMessage(flags.message + "\n\n" + details)
                .setPositiveButton("OK", (d, w) -> d.dismiss())
                .show();
    }

    private String buildDetails(JSONObject e) {
        StringBuilder sb = new StringBuilder();
        sb.append("Data: ").append(e.optString("dateLabel")).append("\n");
        Float fev1 = jsonFloat(e, "fev1");
        Integer spo2 = jsonInt(e, "spo2");
        Float temp = jsonFloat(e, "temp");
        Integer hr = jsonInt(e, "hr");

        if (fev1 != null) sb.append("FEV1: ").append(fev1).append(" L\n");
        if (spo2 != null) sb.append("SpO₂: ").append(spo2).append(" %\n");
        if (temp != null) sb.append("Temp: ").append(temp).append(" °C\n");
        if (hr != null) sb.append("Tętno: ").append(hr).append(" bpm\n");

        sb.append("Samopoczucie: ").append(e.optString("mood")).append("\n");
        String symptoms = e.optString("symptoms", "");
        if (!TextUtils.isEmpty(symptoms)) sb.append("Objawy: ").append(symptoms).append("\n");

        return sb.toString().trim();
    }

    private void clearInputs() {
        if (etFev1 != null) etFev1.setText(null);
        if (etSpo2 != null) etSpo2.setText(null);
        if (etTemp != null) etTemp.setText(null);
        if (etHr != null) etHr.setText(null);
        if (etOtherSymptoms != null) etOtherSymptoms.setText(null);
        if (etMeds != null) etMeds.setText(null);

        for (int i = 0; i < chipGroupSymptoms.getChildCount(); i++) {
            View child = chipGroupSymptoms.getChildAt(i);
            if (child instanceof Chip) ((Chip) child).setChecked(false);
        }
    }

    private String safeText(TextInputEditText et) {
        if (et == null || et.getText() == null) return "";
        return et.getText().toString().trim();
    }

    private Float parseFloatOrNull(TextInputEditText et) {
        String s = safeText(et);
        if (TextUtils.isEmpty(s)) return null;
        s = s.replace(",", ".");
        try { return Float.parseFloat(s); } catch (NumberFormatException e) { return null; }
    }

    private Integer parseIntOrNull(TextInputEditText et) {
        String s = safeText(et);
        if (TextUtils.isEmpty(s)) return null;
        try { return Integer.parseInt(s); } catch (NumberFormatException e) { return null; }
    }

    private Float jsonFloat(JSONObject o, String key) {
        if (!o.has(key) || o.isNull(key)) return null;
        try { return (float) o.getDouble(key); } catch (Exception e) { return null; }
    }

    private Integer jsonInt(JSONObject o, String key) {
        if (!o.has(key) || o.isNull(key)) return null;
        try { return o.getInt(key); } catch (Exception e) { return null; }
    }

    private void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private String formatDatePL(long utcMillis) {
        SimpleDateFormat sdf = new SimpleDateFormat("d MMMM yyyy", new Locale("pl", "PL"));
        return sdf.format(new Date(utcMillis));
    }
}
