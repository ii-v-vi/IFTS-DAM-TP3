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
    }
}