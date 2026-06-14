package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class NuevoNoSocioActivity : AppCompatActivity() {

    lateinit var helper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_no_socio)

        helper = SQLiteHelper(this)

        val evNombre = findViewById<EditText>(R.id.evNombre)
        val evApellido = findViewById<EditText>(R.id.evApellido)
        val evDni = findViewById<EditText>(R.id.evDni)
        val evTelefono = findViewById<EditText>(R.id.evTelefono)
        val evMail = findViewById<EditText>(R.id.evMail)
        val chkFicha = findViewById<CheckBox>(R.id.chkFichaMedica)
        val btnContinuar = findViewById<Button>(R.id.btnContinuar)

        val btnFecha = findViewById<Button>(R.id.btnFecha)
        val tvFecha = findViewById<TextView>(R.id.tvFecha)

        btnFecha.setOnClickListener {
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

                    Toast.makeText(this, "Fecha: $fechaFormateada", Toast.LENGTH_LONG).show()
                }
                .setNegativeButton("Cancelar", null)
                .create()
            dialogFecha.show()
        }

        btnContinuar.setOnClickListener {
            if (
                chkFicha.isChecked &&
                evNombre.text.isNotEmpty() &&
                evApellido.text.isNotEmpty() &&
                evDni.text.isNotEmpty() &&
                evTelefono.text.isNotEmpty() &&
                evMail.text.isNotEmpty() &&
                tvFecha.text != "--/--/--"
            ) {
                val noSocio = NoSocio(
                    nombre = evNombre.text.toString(),
                    apellido = evApellido.text.toString(),
                    dni = evDni.text.toString(),
                    telefono = evTelefono.text.toString(),
                    mail = evMail.text.toString(),
                    fechaNacimiento = tvFecha.text.toString(),
                    fichaMedica = chkFicha.isChecked
                )

                RepositorioNoSocios.listaNoSocios.add(noSocio)
                helper.insertarNoSocio(noSocio)

                Toast.makeText(this, "No Socio guardado correctamente", Toast.LENGTH_SHORT).show()

                val intentMenu = Intent(this, MenuPrincipalActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                }
                startActivity(intentMenu)

                finish()

            } else {
                Toast.makeText(this, "Información incompleta", Toast.LENGTH_LONG).show()
            }
        }
    }
}