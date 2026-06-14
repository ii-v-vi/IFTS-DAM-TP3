package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class NuevoSocioActivity : AppCompatActivity() {

    lateinit var helper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nuevo_socio)

        // Inicializamos el helper de la Base de Datos
        helper = SQLiteHelper(this)

        // Capturamos los elementos del xml
        val evNombre = findViewById<EditText>(R.id.evNombre)
        val evApellido = findViewById<EditText>(R.id.evApellido)
        val evDni = findViewById<EditText>(R.id.evDni)
        val evTelefono = findViewById<EditText>(R.id.evTelefono)
        val evMail = findViewById<EditText>(R.id.evMail)
        val chkFicha = findViewById<CheckBox>(R.id.chkFichaMedica)
        val btnContinuar = findViewById<Button>(R.id.btnContinuar)
        val btnFecha = findViewById<Button>(R.id.btnFecha)
        val tvFecha = findViewById<TextView>(R.id.tvFecha)

        // ---------- SELECCIONAR FECHA ----------
        btnFecha.setOnClickListener {
            val modalSeleccionarFecha = layoutInflater.inflate(R.layout.dialog_seleccionar_fecha, null)
            val calendario = modalSeleccionarFecha.findViewById<DatePicker>(R.id.calendario)

            AlertDialog.Builder(this)
                .setTitle("Fecha de nacimiento")
                .setView(modalSeleccionarFecha)
                .setPositiveButton("Guardar") { _, _ ->
                    val dia = calendario.dayOfMonth
                    val mes = calendario.month + 1
                    val anio = calendario.year

                    val fechaFormateada = "$dia/$mes/$anio"
                    tvFecha.text = fechaFormateada
                    Toast.makeText(this, "Fecha seleccionada: $fechaFormateada", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancelar", null)
                .create()
                .show()
        }

        // ---------- BOTON CONTINUAR (GUARDADO EN BD Y RETORNO) ----------
        btnContinuar.setOnClickListener {
            val dniIngresado = evDni.text.toString().trim()

            if (
                chkFicha.isChecked &&
                evNombre.text.isNotEmpty() &&
                evApellido.text.isNotEmpty() &&
                dniIngresado.isNotEmpty() &&
                evTelefono.text.isNotEmpty() &&
                evMail.text.isNotEmpty() &&
                tvFecha.text != "--/--/--"
            ) {

                // 1. Creamos la instancia con los datos ingresados
                val socio = Socio(
                    nombre = evNombre.text.toString().trim(),
                    apellido = evApellido.text.toString().trim(),
                    dni = dniIngresado,
                    telefono = evTelefono.text.toString().trim(),
                    mail = evMail.text.toString().trim(),
                    fechaNacimiento = tvFecha.text.toString(),
                    fichaMedica = chkFicha.isChecked
                )

                // 2. Guardamos en el Singleton en memoria (Tu repositorio)
                RepositorioSocios.listaSocios.add(socio)

                // 3. Guardamos en la Base de Datos SQLite y capturamos el ID asignado
                val idGenerado = helper.insertarSocio(socio)

                if (idGenerado != -1L) {
                    Toast.makeText(
                        this,
                        "Socio guardado con éxito",
                        Toast.LENGTH_SHORT
                    ).show()

                    // 4. NUEVO ENFOQUE MODULAR: Forzamos la vuelta al Menú Principal limpiando la pila
                    val intentMenu = Intent(this, MenuPrincipalActivity::class.java).apply {
                        // FLAG_ACTIVITY_CLEAR_TOP barre cualquier activity residual del alta
                        // FLAG_ACTIVITY_SINGLE_TOP reutiliza la instancia del menú si ya estaba viva
                        flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                    }
                    startActivity(intentMenu)

                    // Destruimos el formulario definitivamente
                    finish()
                } else {
                    Toast.makeText(this, "Error al guardar en la base de datos", Toast.LENGTH_LONG).show()
                }

            } else {
                // Validación extra por si no tildaron la ficha médica requerida corporativa
                if (!chkFicha.isChecked) {
                    Toast.makeText(this, "Es obligatorio presentar la Ficha Médica", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}