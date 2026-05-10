package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EleccionNuevoMiembroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eleccion_nuevo_miembro)

        val btnVolver = findViewById<TextView>(R.id.tvVolver)
        val btnSocio = findViewById<Button>(R.id.btnSocio)
        val btnNoSocio = findViewById<Button>(R.id.btnNoSocio)



        // ---------- BOTON VOLVER ----------
        btnVolver.setOnClickListener {
            val intentarVolver = Intent(this, ActivityListaMiembros::class.java)
            startActivity(intentarVolver)
        }
        // ---------- BOTON VOLVER ----------



        // ---------- BOTON SOCIO ----------
        btnSocio.setOnClickListener {
            val intentarSocio = Intent(this, NuevoSocioActivity::class.java)
            startActivity(intentarSocio)
        }
        // ---------- BOTON SOCIO ----------



        // ---------- BOTON NO SOCIO ----------
        btnNoSocio.setOnClickListener {
            val intentarNoSocio = Intent(this, NuevoNoSocioActivity::class.java)
            startActivity(intentarNoSocio)
        }
        // ---------- BOTON SOCIO ----------

    }
}