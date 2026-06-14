package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EleccionNuevoMiembroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eleccion_nuevo_miembro)

        val btnSocio = findViewById<Button>(R.id.btnSocio)
        val btnNoSocio = findViewById<Button>(R.id.btnNoSocio)

        btnSocio.setOnClickListener {
            val intent = Intent(this, NuevoSocioActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnNoSocio.setOnClickListener {
            val intent = Intent(this, NuevoNoSocioActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}