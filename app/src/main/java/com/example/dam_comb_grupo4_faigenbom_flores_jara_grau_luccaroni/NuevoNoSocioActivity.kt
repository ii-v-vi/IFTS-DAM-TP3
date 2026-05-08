package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Button
import android.widget.Toast
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity


class NuevoNoSocioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_no_socio)

        var btnContinuar = findViewById<Button>(R.id.btnContinuar)
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
            card.setBackgroundColor(Color.parseColor("#E3F7D4"))

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
                var intentarCobrarActividad1 = Intent(this, CobrarActividad1Activity::class.java)
                startActivity(intentarCobrarActividad1)
            }
        }
        // ---------- BOTON CONTINUAR ----------
    }
}