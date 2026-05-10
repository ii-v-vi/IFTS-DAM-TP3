package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView

class PerfilSocioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_socio)

        val btnMiembros = findViewById<LinearLayout>(R.id.btn_volver_a_miembros)
        val btnCobrarCuota = findViewById<CardView>(R.id.btn_datos_socios_cobrar_cuota)
        val btnEditarSocio = findViewById<CardView>(R.id.btn_datos_socios_editar)
        val btnInactivarSocio = findViewById<CardView>(R.id.btn_datos_socios_inactivar)
        val navInicio = findViewById<LinearLayout>(R.id.nav_inicio)
        val navMiembros = findViewById<LinearLayout>(R.id.nav_miembros)
        val navCobrar = findViewById<LinearLayout>(R.id.nav_cobrar)
        val navMas = findViewById<LinearLayout>(R.id.nav_mas)


        // --- BOTON VOLVER A MIEMBROS
        btnMiembros.setOnClickListener {
            val volverAMiembros = Intent(this, ActivityListaMiembros::class.java)
            startActivity(volverAMiembros)
        }

        // --- BOTON COBRAR CUOTA
        btnCobrarCuota.setOnClickListener {
            val intentarCobrarCuota = Intent(this, CobrarCuotaActivity::class.java)
            startActivity(intentarCobrarCuota)
        }

        // --- BOTON EDITAR SOCIO
        btnEditarSocio.setOnClickListener {
            Toast.makeText(this, "EN PROGRESO...", Toast.LENGTH_SHORT).show()
        }


        // --- BOTON INACTIVAR SOCIO
        btnInactivarSocio.setOnClickListener {
            Toast.makeText(this, "EN PROGRESO...", Toast.LENGTH_SHORT).show()
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