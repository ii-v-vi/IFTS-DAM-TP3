package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Ruteo: ir a NuevoMiembroActivity
        val btnIntentNuevoMiembro = findViewById<Button>(R.id.btnIntentNuevoMiembro)
        btnIntentNuevoMiembro.setOnClickListener {
            val intentar = Intent(this, NuevoMiembroActivity::class.java)
            startActivity(intentar)
        }
    }
}