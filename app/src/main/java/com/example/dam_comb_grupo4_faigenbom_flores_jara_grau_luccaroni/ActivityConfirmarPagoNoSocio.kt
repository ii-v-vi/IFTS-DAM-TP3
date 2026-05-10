package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ActivityConfirmarPagoNoSocio : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmar_pago_no_socio)


        val btnContinuar = findViewById<LinearLayout>(R.id.btn_pago_registrado_compartir_comprobante)
        val btnCancelar = findViewById<LinearLayout>(R.id.btn_cancelar)
        val btnVolver = findViewById<LinearLayout>(R.id.btn_volver_a_inicio)


        // --- BOTON CONFIRMAR ---
        btnContinuar.setOnClickListener {
            val intentarCuotaCobrada = Intent(this, ActividadCobradaActivity::class.java)
            startActivity(intentarCuotaCobrada)
        }

        // --- BOTON VOLVER ---
        btnCancelar.setOnClickListener {
            val intentarVolver = Intent(this, MenuPrincipalActivity::class.java)
            startActivity(intentarVolver)
        }

        // --- BOTON CANCELAR ---
        btnVolver.setOnClickListener {

            val intentarCancelar = Intent(this, EleccionActividadActivity::class.java)
            startActivity(intentarCancelar)
        }
    }
}