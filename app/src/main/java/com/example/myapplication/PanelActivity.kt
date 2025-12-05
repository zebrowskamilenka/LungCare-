package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class PanelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_panel)

        //val btnMapa: Button = findViewById(R.id.btnMapa)
        //val btnDzienniczek: Button = findViewById(R.id.btnDzienniczek)
        //val btnLeki: Button = findViewById(R.id.btnLeki)
        //val btnProfil: Button = findViewById(R.id.btnProfil)

        // Mapa UCK
      //  btnMapa.setOnClickListener {
         //   startActivity(Intent(this, MapsActivity::class.java))
        //}

        // Dzienniczek
       // btnDzienniczek.setOnClickListener {
        //    startActivity(Intent(this, DairyActivity::class.java))
      //  }

        // Leki
        //btnLeki.setOnClickListener {
            //startActivity(Intent(this, MedsActivity::class.java))
       // }

        // Profil
       // btnProfil.setOnClickListener {
            //startActivity(Intent(this, ProfileActivity::class.java))
     //   }


    }
}
