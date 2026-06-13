package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ActividadCobradaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad_cobrada)

        val nombre = intent.getStringExtra("NOMBRE_COMPLETO") ?: "IA"
        val monto = intent.getStringExtra("ACTIVIDAD_PRECIO") ?: "0"
        val medio = intent.getStringExtra("FORMA_PAGO") ?: "Efectivo"

        // Calcular iniciales a partir del nombre completo enviado
        val partes = nombre.split(" ")
        val iniciales = (partes.getOrNull(0)?.firstOrNull() ?: 'I').toString() +
                (partes.getOrNull(1)?.firstOrNull() ?: 'A').toString()

        findViewById<TextView>(R.id.txt_iniciales_no_socio).text = iniciales.uppercase()
        findViewById<TextView>(R.id.pago_registrado_monto_pagado).text = "$$monto"
        findViewById<TextView>(R.id.pago_registrado_medio_de_pago).text = medio

        findViewById<View>(R.id.btn_pago_registrado_volver_a_inicio).setOnClickListener {
            finish()
        }
    }
}