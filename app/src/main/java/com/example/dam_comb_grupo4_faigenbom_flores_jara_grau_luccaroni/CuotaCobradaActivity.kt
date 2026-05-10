package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CuotaCobradaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cuota_cobrada)

        val btn_pago_registrado_volver_a_inicio = findViewById<LinearLayout>(R.id.btn_pago_registrado_volver_a_inicio)
        val btnCompartir = findViewById<LinearLayout>(R.id.btn_pago_registrado_compartir_comprobante)

        // ---------- BOTON COMPARTIR ----------
        btnCompartir.setOnClickListener {
            Toast.makeText(this, "EN PROGRESO...", Toast.LENGTH_SHORT).show()
        }
        // ---------- BOTON COMPARTIR ----------



        // ---------- BOTON VOLVER ----------
        btn_pago_registrado_volver_a_inicio.setOnClickListener {
            val intentarVolver = Intent(this, MenuPrincipalActivity::class.java)
            startActivity(intentarVolver)
        }
        // ---------- BOTON VOLVER ----------
    }
}