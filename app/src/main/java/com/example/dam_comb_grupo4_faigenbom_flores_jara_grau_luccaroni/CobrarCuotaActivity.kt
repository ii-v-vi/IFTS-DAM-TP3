package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CobrarCuotaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cobrar_cuota)


        val btnVolver = findViewById<TextView>(R.id.tvVolver)
        val btnContinuar = findViewById<Button>(R.id.btnContinuar)
        val rbtEfectivo = findViewById<RadioButton>(R.id.rbtEfectivo)
        val rbtUnaCuota = findViewById<RadioButton>(R.id.rbtTarjetaUnaCuota)
        val rbtTresCuotas = findViewById<RadioButton>(R.id.rbtTarjetaTresCutoas)
        val rbtSeisCuotas = findViewById<RadioButton>(R.id.rbtTarjetaSeisCutoas)
        var medioDePago = ""
        val efectivo = "efectivo"
        val unaCuota = "unaCuota"
        val tresCuotas = "tresCuotas"
        val seisCuotas = "seisCuotas"


        // ---------- RADIOBUTTONS----------
        rbtEfectivo.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) medioDePago = efectivo
        }

        rbtUnaCuota.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) medioDePago = unaCuota
        }

        rbtTresCuotas.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) medioDePago = unaCuota
        }

        rbtSeisCuotas.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) medioDePago = seisCuotas
        }
        // ---------- RADIOBUTTONS----------



        // ---------- BOTON CONTINUAR ----------
        btnContinuar.setOnClickListener {
            if(medioDePago == ""){
                Toast.makeText(this, "Debe seleccionar un medio de pago.", Toast.LENGTH_SHORT).show()
            }
            else{
                 // val intentarConfirmarPago = Intent()
                Toast.makeText(this, "INTENT CONFIRMAR PAGO", Toast.LENGTH_SHORT).show()
            }
        }
        // ---------- BOTON CONTINUAR ----------



        // ---------- BOTON VOLVER ----------
        btnVolver.setOnClickListener {
            val intentarVolver = Intent(this, NuevoSocioActivity::class.java)
            startActivity(intentarVolver)
        }
        // ---------- BOTON VOLVER ----------
    }
}