package com.example.myapplication.ui.gallery;

public class Medication {

        private String name;
        private String dose;
        private String time;

        // Konstruktor z 3 parametrami
        public Medication(String name, String dose, String time) {
            this.name = name;
            this.dose = dose;
            this.time = time;
        }

        // Konstruktor z 1 parametrem (dodaj ten)
        public Medication(String name) {
            this.name = name;
            this.dose = "";
            this.time = "";
        }

        public String getName() {
            return name;
        }

        public String getDose() {
            return dose;
        }

        public String getTime() {
            return time;
        }
    }

