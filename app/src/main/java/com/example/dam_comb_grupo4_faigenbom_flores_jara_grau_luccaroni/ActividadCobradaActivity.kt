package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ActividadCobradaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_actividad_cobrada)

        val nombre = intent.getStringExtra("NOMBRE_COMPLETO") ?: "Invitado"
        val actividad = intent.getStringExtra("ACTIVIDAD_NAME") ?: intent.getStringExtra("ACTIVIDAD_NOMBRE") ?: "Pase Diario"
        val monto = intent.getStringExtra("ACTIVIDAD_PRECIO") ?: "0"
        val medio = intent.getStringExtra("FORMA_PAGO") ?: "Efectivo"

        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        val fechaActual = sdf.format(Date())

        val partes = nombre.split(" ")
        val iniciales = (partes.getOrNull(0)?.firstOrNull() ?: 'I').toString() +
                (partes.getOrNull(1)?.firstOrNull() ?: 'A').toString()

        findViewById<TextView>(R.id.txt_iniciales_no_socio).text = iniciales.uppercase()
        findViewById<TextView>(R.id.pago_registrado_monto_pagado).text = if (monto.contains("$")) monto else "$$monto"
        findViewById<TextView>(R.id.pago_registrado_medio_de_pago).text = medio

        findViewById<View>(R.id.btn_pago_registrado_compartir_comprobante).setOnClickListener {
            val textoACompartir = """
                COMPROBANTE DE ACTIVIDAD (NO SOCIO) - TITAN CLUB
                -------------------------------------
                Miembro: $nombre
                Concepto: Pase Diario - $actividad
                Medio de Pago: $medio
                Fecha: $fechaActual
                Monto: ${if (monto.contains("$")) monto else "$$monto"}
                Estado: ✓ PAGADO
                -------------------------------------
                ¡Gracias por tu visita!
            """.trimIndent()

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, textoACompartir)
            }
            startActivity(Intent.createChooser(shareIntent, "Compartir comprobante vía:"))
        }

        findViewById<View>(R.id.btn_pago_registrado_volver_a_inicio).setOnClickListener {
            val intentarVolver = Intent(this, MenuPrincipalActivity::class.java)

            intentarVolver.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intentarVolver)
            finish()
        }
    }
}