package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.internal.TextScale


class ActivityConfirmarPagoSocio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmar_pago_socio)

        val btnVolver = findViewById<TextView>(R.id.tvVolver)




        // --- LOGICA FOOTER ---
        navInicio.setOnClickListener {
            val intentarInicio = Intent(this, MenuPrincipalActivity::class.java)
            startActivity(intentarInicio)
        }
        navMiembros.setOnClickListener {
            val intentarInicio = Intent(this, ActivityListaMiembros::class.java)
            startActivity(intentarInicio)
        }
        navCobrar.setOnClickListener {
            val intentarInicio = Intent(this, CobrarCuotaActivity::class.java)
            startActivity(intentarInicio)
        }
        navMas.setOnClickListener {
            Toast.makeText(this, "EN PROGRESO...", Toast.LENGTH_SHORT).show()
        }
    }
}