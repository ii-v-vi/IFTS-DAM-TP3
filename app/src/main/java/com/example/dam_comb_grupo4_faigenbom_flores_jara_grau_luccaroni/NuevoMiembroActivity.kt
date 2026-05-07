package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class NuevoMiembroActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_miembro)

        var tipoMiembro = ""
        // Capturamos los elementos del xml que vamos a necesitar
        val evNombre = findViewById<EditText>(R.id.evNombre)
        val evApellido = findViewById<EditText>(R.id.evApellido)
        val evDni = findViewById<EditText>(R.id.evDni)
        val evTelefono = findViewById<EditText>(R.id.evTelefono)
        val evMail = findViewById<EditText>(R.id.evMail)
        val chkFicha = findViewById<CheckBox>(R.id.chkFichaMedica)
        val btnSocio = findViewById<Button>(R.id.btnSocio)
        val btnNoSocio = findViewById<Button>(R.id.btnNoSocio)
        val btnContinuar = findViewById<Button>(R.id.btnContinuar)



        // ---------- BOTONES SOCIO / NO SOCIO ----------
        btnSocio.backgroundTintList = null
        btnNoSocio.backgroundTintList = null
        val colorActivo = Color.parseColor("#D63A27")
        val colorNormal = Color.parseColor("#0C0F14")

        fun seleccionarTipo(seleccionado: Button, otro: Button) {
            seleccionado.setBackgroundColor(colorActivo)
            otro.setBackgroundColor(colorNormal)
        }

        btnSocio.setOnClickListener {
            seleccionarTipo(btnSocio, btnNoSocio)
            tipoMiembro = "socio"
        }

        btnNoSocio.setOnClickListener {
            seleccionarTipo(btnNoSocio, btnSocio)
            tipoMiembro = "nosocio"
        }
        // ---------- BOTONES SOCIO / NO SOCIO ----------



        // ---------- FECHA ----------
        // --- Creamos un dialog para que el usuario pueda selecionar una fecha y nosotros podamos capturarla
        val btnFecha = findViewById<Button>(R.id.btnFecha)
        val tvFecha = findViewById<TextView>(R.id.tvFecha)

        btnFecha.setOnClickListener {
            //btnContinuar.setBackgroundColor(colorActivo)
            val modalSeleccionarFecha = layoutInflater.inflate(R.layout.dialog_seleccionar_fecha, null)
            val calendario = modalSeleccionarFecha.findViewById<DatePicker>(R.id.calendario)

            val dialogFecha = AlertDialog.Builder(this)
                .setTitle("Fecha de nacimiento")
                .setView(modalSeleccionarFecha)
                .setPositiveButton("Guardar") { _, _ ->
                    val dia = calendario.dayOfMonth
                    val mes = calendario.month + 1
                    val anio = calendario.year

                    val fechaFormateada = "$dia/$mes/$anio"
                    tvFecha.text = fechaFormateada

                    Toast.makeText(this, "Fecha: $fechaFormateada", Toast.LENGTH_LONG)
                        .show()
                }
                .setNegativeButton("Cancelar", null)
                .create()
            dialogFecha.show()
        }
        // ---------- FECHA ----------




        // ---------- BOTON CONTINUAR ----------
        btnContinuar.setOnClickListener {
            if (
                (chkFicha.isChecked) &&
                (evNombre.text.isNotEmpty()) &&
                (evApellido.text.isNotEmpty()) &&
                (evDni.text .isNotEmpty()) &&
                (evTelefono.text .isNotEmpty()) &&
                (evMail.text .isNotEmpty()) &&
                (tvFecha.text != "--/--/--") &&
                (tipoMiembro.isNotEmpty())
                ) {
                Toast.makeText(this , "Creando usuario", Toast.LENGTH_LONG).show()

                // Switch para definir a qué activity se dirige (socio o no socio)
                when(tipoMiembro){
                    "socio" -> {
                        val intentNuevoSocio = Intent(this, NuevoSocioActivity::class.java)
                        startActivity(intentNuevoSocio)
                    }
                    "nosocio" -> {
                        val intentNuevoNoSocio = Intent(this, NuevoNoSocioActivity::class.java)
                        startActivity(intentNuevoNoSocio)
                    }
                }
            } else {
                Toast.makeText(this , "Información incompleta", Toast.LENGTH_LONG).show()

            }
        }
        // ---------- BOTON CONTINUAR ----------



    }
}