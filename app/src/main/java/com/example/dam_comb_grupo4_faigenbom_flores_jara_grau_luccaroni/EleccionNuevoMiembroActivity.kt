package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EleccionNuevoMiembroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_eleccion_nuevo_miembro)

        val btnVolver = findViewById<TextView>(R.id.tvVolver)
        val btnSocio = findViewById<Button>(R.id.btnSocio)
        val btnNoSocio = findViewById<Button>(R.id.btnNoSocio)



        // ---------- BOTON VOLVER ----------
        btnVolver.setOnClickListener {
            val intentarVolver = Intent(this, ActivityListaMiembros::class.java)
            startActivity(intentarVolver)
        }
        // ---------- BOTON VOLVER ----------



        // ---------- BOTON SOCIO ----------
         btnSocio.setOnClickListener {
            val intentarSocio = Intent(this, NuevoSocioActivity::class.java)
            startActivity(intentarSocio)
        }
        // ---------- BOTON SOCIO ----------
        /*btnSocio.setOnClickListener {
            val vista= layoutInflater.inflate(R.layout.dialog_socio,null)
            val evNombre = vista.findViewById<EditText>(R.id.evNombre)
            val evApellido = vista.findViewById<EditText>(R.id.evApellido)
            val evDni = vista.findViewById<EditText>(R.id.evDni)
            val evTelefono = vista.findViewById<EditText>(R.id.evTelefono)
            val evMail= vista.findViewById<EditText>(R.id.evMail)
            //val evFecha = vista.findViewById<EditText>(R.id.tvFecha)
            //val evFichaMedica = vista.findViewById<EditText>(R.id.chkFichaMedica)

            val dialog = AlertDialog.Builder(this)
                .setTitle("Nuevo Socio")
                .setView(vista)
                .setPositiveButton("Guardar y continuar"){ _, _ ->
                    val nombreToString = evNombre.text.toString()
                    val apellidoToString = evApellido.text.toString()
                    val DniToString = evDni.text.toString()
                    val TelefonoToString = evTelefono.text.toString()
                    val MailToString = evMail.text.toString()
                    Toast.makeText(this,"Socio Agregado", Toast.LENGTH_LONG).show()
                }
                .setNegativeButton("Cancelar", null)
                .create()
            dialog.show()
        }*/


        // ---------- BOTON NO SOCIO ----------
        btnNoSocio.setOnClickListener {
            val intentarNoSocio = Intent(this, NuevoNoSocioActivity::class.java)
            startActivity(intentarNoSocio)
        }
        // ---------- BOTON SOCIO ----------

    }
}