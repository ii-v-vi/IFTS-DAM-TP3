package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class EleccionActividadActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eleccion_actividad)

        var btnContinuar = findViewById<Button>(R.id.btnContinuar)
        var btnVolver = findViewById<TextView>(R.id.tvVolver)
        val cardZumba = findViewById<LinearLayout>(R.id.llCardZumba)
        val cardPilates = findViewById<LinearLayout>(R.id.llCardPilates)
        val cardElongacion = findViewById<LinearLayout>(R.id.llCardElongacion)
        val cardKarate = findViewById<LinearLayout>(R.id.llCardKarate)
        val cardSpinning = findViewById<LinearLayout>(R.id.llCardSpinning)
        val cardHit = findViewById<LinearLayout>(R.id.llCardHit)
        var cardSeleccionada : LinearLayout? = null
        var todasLascCards = listOf<LinearLayout>(cardZumba, cardPilates, cardElongacion, cardKarate, cardSpinning, cardHit)

        // ---------- SELECCIONAR LAS CARDS ----------
        // Funcion para pintar la card seleccionada y despintar la anterior (si la hay)
        fun seleccionarCard(card: LinearLayout){

            // Despintamos la card seleccionada previamente
            cardSeleccionada?.setBackgroundColor(Color.parseColor("#DF4A47"))

            // Pintamos la nueva card elegida
            card.setBackgroundColor(Color.parseColor("#3AFF12"))

            // Remplazamos la instancia de la cardSeleccionada vieja por la nueva
            cardSeleccionada = card
        }

        // Aplicamos un forEach para recorrer el array de cards y aplicar la funcion de pintar en cada una
        todasLascCards.forEach { card ->
            card.setOnClickListener {
                seleccionarCard(card)
            }
        }
        // ---------- SELECCIONAR LAS CARDS ----------


        // ---------- BOTON CONTINUAR ----------
        btnContinuar.setOnClickListener {
            if(cardSeleccionada == null){
                Toast.makeText(this, "Debe seleccionar una actividad", Toast.LENGTH_LONG).show()
            }
            else{
                val intentCobrarActividad = Intent(this, ActivityConfirmarPagoNoSocio::class.java)
                startActivity(intentCobrarActividad)
            }
        }
        // ---------- BOTON CONTINUAR ----------


        // ---------- BOTON VOLVER ----------
        btnVolver.setOnClickListener {
            val intentarVolver = Intent(this, NuevoNoSocioActivity::class.java)
            startActivity(intentarVolver)
        }
        // ---------- BOTON VOLVER ----------
    }
}