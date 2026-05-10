package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MenuPrincipalActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)

        val btnVencenHoy = findViewById<LinearLayout>(R.id.btn_aviso_socios_vencen_hoy)
        val navInicio = findViewById<LinearLayout>(R.id.nav_inicio)
        val navMiembros = findViewById<LinearLayout>(R.id.nav_miembros)
        val navCobrar = findViewById<LinearLayout>(R.id.nav_cobrar)
        val navMas = findViewById<LinearLayout>(R.id.nav_mas)


        // --- BOTON SOCIOS VENCEN HOY
        btnVencenHoy.setOnClickListener {
            val intentarVencenHoy = Intent(this, ActivityVencenHoy::class.java)
            startActivity(intentarVencenHoy)
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