package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.content.Intent
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView


class ActivityListaMiembros : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_miembros)

        val btnVolver = findViewById<TextView>(R.id.tvVolver)
        val btnAgregarMiembro = findViewById<Button>(R.id.btnAgregarMiembro)
        val navInicio = findViewById<LinearLayout>(R.id.nav_inicio)
        val navMiembros = findViewById<LinearLayout>(R.id.nav_miembros)
        val navCobrar = findViewById<LinearLayout>(R.id.nav_cobrar)
        val navMas = findViewById<LinearLayout>(R.id.nav_mas)

        val cardview1 = findViewById<Button>(R.id.cardview1)
        val cardview2 = findViewById<Button>(R.id.cardview2)
        val cardview3 = findViewById<Button>(R.id.cardview3)
        val cardview4 = findViewById<TextView>(R.id.cardview4)
        val cardview5 = findViewById<TextView>(R.id.cardview5)


        // --- BOTON AGREGAR MIEMBRO
        btnAgregarMiembro.setOnClickListener {
            val intentarAgregarMiembro = Intent(this, EleccionNuevoMiembroActivity::class.java)
            startActivity(intentarAgregarMiembro)
        }


        // ---- BOTONES SOBRE LAS CARDS
        cardview1.setOnClickListener {
            val intentVencenHoy= Intent(this, ActivityVencenHoy::class.java)
            startActivity(intentVencenHoy)
        }

        cardview2.setOnClickListener {
            val intentVencenHoy= Intent(this, ActivityVencenHoy::class.java)
            startActivity(intentVencenHoy)
        }

        cardview3.setOnClickListener {
            val intentVencenHoy= Intent(this, ActivityVencenHoy::class.java)
            startActivity(intentVencenHoy)
        }

        cardview4.setOnClickListener {
            val intentPerfilSocio= Intent(this, PerfilSocioActivity::class.java)
            startActivity(intentPerfilSocio)
        }
        cardview5.setOnClickListener {
            val intentPerfilNoSocio= Intent(this, PerfilNoSocioActivity::class.java)
            startActivity(intentPerfilNoSocio)
        }
        // ---- BOTONES DE FILTRADO ----
        val btnSocios = findViewById<Button>(R.id.btnSocios)
        val btnNoSocios = findViewById<Button>(R.id.btnNoSocios)

        btnSocios.setOnClickListener {
            val intent = Intent(this, ActivityListaSocios::class.java)
            startActivity(intent)
        }

        btnNoSocios.setOnClickListener {
            val intent = Intent(this, ActivityListaNoSocios::class.java)
            startActivity(intent)
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