package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.os.Bundle
import android.widget.TextView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Toast


class ActivityConfirmarPagoSocio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmar_pago_socio)

        val btnVolver = findViewById<LinearLayout>(R.id.tvVolver)
        val btnCancelar = findViewById<LinearLayout>(R.id.btnCancelar)
        val navInicio = findViewById<LinearLayout>(R.id.nav_inicio)
        val navMiembros = findViewById<LinearLayout>(R.id.nav_miembros)
        val navCobrar = findViewById<LinearLayout>(R.id.nav_cobrar)
        val navMas = findViewById<LinearLayout>(R.id.nav_mas)
        val btnConfirmarPago = findViewById<LinearLayout>(R.id.btn_pago_registrado_compartir_comprobante)


        // --- BOTON CONFIRMAR ---
        btnConfirmarPago.setOnClickListener {
            val intentarCuotaCobrada = Intent(this, CuotaCobradaActivity::class.java)
            startActivity(intentarCuotaCobrada)
        }


        // --- BOTON VOLVER ---
        btnVolver.setOnClickListener {
            val intentarVolver = Intent(this, CobrarCuotaActivity::class.java)
            startActivity(intentarVolver)
        }

        // --- BOTON CANCELAR ---
        btnCancelar.setOnClickListener {

            val intentarCancelar = Intent(this, ActivityListaMiembros::class.java)
            startActivity(intentarCancelar)
        }


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