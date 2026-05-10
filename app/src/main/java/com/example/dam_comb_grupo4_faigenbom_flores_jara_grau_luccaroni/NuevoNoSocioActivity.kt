package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.graphics.Color
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Button
import android.widget.Toast
import android.content.Intent
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class NuevoNoSocioActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_no_socio)


        // Capturamos los elementos del xml que vamos a necesitar
        val evNombre = findViewById<EditText>(R.id.evNombre)
        val evApellido = findViewById<EditText>(R.id.evApellido)
        val evDni = findViewById<EditText>(R.id.evDni)
        val evTelefono = findViewById<EditText>(R.id.evTelefono)
        val evMail = findViewById<EditText>(R.id.evMail)
        val chkFicha = findViewById<CheckBox>(R.id.chkFichaMedica)
        val btnContinuar = findViewById<Button>(R.id.btnContinuar)
        val btnVolver = findViewById<TextView>(R.id.tvVolver)
        val navInicio = findViewById<LinearLayout>(R.id.nav_inicio)
        val navMiembros = findViewById<LinearLayout>(R.id.nav_miembros)
        val navCobrar = findViewById<LinearLayout>(R.id.nav_cobrar)
        val navMas = findViewById<LinearLayout>(R.id.nav_mas)




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
                (tvFecha.text != "--/--/--")
            ) {
                Toast.makeText(this , "Creando usuario", Toast.LENGTH_LONG).show()
                val intentNuevoNoSocio = Intent(this, EleccionActividadActivity::class.java)
                startActivity(intentNuevoNoSocio)


            } else {
                Toast.makeText(this , "Información incompleta", Toast.LENGTH_LONG).show()

            }
        }
        // ---------- BOTON CONTINUAR ----------

        // ---------- BOTON VOLVER ----------
        btnVolver.setOnClickListener {
            val intentarVolver = Intent(this, EleccionNuevoMiembroActivity::class.java)
            startActivity(intentarVolver)
        }
        // ---------- BOTON VOLVER ----------


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