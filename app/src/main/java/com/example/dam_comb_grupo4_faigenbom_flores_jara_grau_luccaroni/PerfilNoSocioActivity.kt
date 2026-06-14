package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import java.util.Calendar

class PerfilNoSocioActivity : AppCompatActivity() {

    private lateinit var helper: SQLiteHelper
    private var dniRecibido: String? = null

    // Declaramos las vistas de la cabecera y datos principales
    private lateinit var txtIniciales: TextView
    private lateinit var txtNombreCompleto: TextView
    private lateinit var tvDni: TextView
    private lateinit var tvEmail: TextView
    private lateinit var tvTelefono: TextView
    private lateinit var tvNacimiento: TextView
    private lateinit var tvActividad: TextView

    // Declaramos las vistas del historial inferior
    private lateinit var tvUltimoPago: TextView
    private lateinit var tvUltimaActividad: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil_no_socio)

        helper = SQLiteHelper(this)

        // Vinculación de vistas de cabecera y datos
        val btnVolver = findViewById<LinearLayout>(R.id.btn_volver_a_miembros)
        txtIniciales = findViewById<TextView>(R.id.txt_iniciales_no_socio)
        txtNombreCompleto = findViewById<TextView>(R.id.txt_nombre_no_socio)

        tvDni = findViewById<TextView>(R.id.datos_no_socio_dni)
        tvEmail = findViewById<TextView>(R.id.datos_no_socio_email)
        tvTelefono = findViewById<TextView>(R.id.datos_no_socio_telefono)
        tvNacimiento = findViewById<TextView>(R.id.datos_no_socio_nacimiento)
        tvActividad = findViewById<TextView>(R.id.datos_no_socio_ficha_medica)

        // Vinculación de vistas del Historial de Pagos
        tvUltimoPago = findViewById<TextView>(R.id.datos_no_socio_ultimo_pago)
        tvUltimaActividad = findViewById<TextView>(R.id.datos_no_socio_ultima_actividad)

        // Vinculación de botones de acción
        val btnCobrarActividad = findViewById<CardView>(R.id.btn_datos_no_socios_cobrar_actividad)
        val btnEditarNoSocio = findViewById<CardView>(R.id.btn_datos_no_socios_editar)

        // ---------- SOLUCIÓN XML: VINCULACIÓN DEL NUEVO BOTÓN ----------
        val btnEliminarNoSocio = findViewById<CardView>(R.id.btn_datos_no_socios_eliminar)

        dniRecibido = intent.getStringExtra("MEMBER_DNI")

        // Primera carga de datos
        cargarDatosNoSocio()

        btnVolver.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        btnCobrarActividad.setOnClickListener {
            val intentActividad = Intent(this, EleccionActividadActivity::class.java)
            intentActividad.putExtra("MEMBER_DNI", dniRecibido)
            startActivity(intentActividad)
        }

        btnEditarNoSocio.setOnClickListener {
            mostrarDialogoEditar()
        }

        // ---------- SOLUCIÓN KOTLIN: DISPARADOR DE ACCIÓN ----------
        btnEliminarNoSocio.setOnClickListener {
            mostrarDialogoConfirmarEliminar()
        }
    }

    // Encapsulado en una función para poder llamarlo de nuevo luego de editar
    private fun cargarDatosNoSocio() {
        if (dniRecibido != null) {
            val noSocio = helper.obtenerNoSocioPorDni(dniRecibido!!)

            if (noSocio != null) {
                // Seteo de datos principales
                txtNombreCompleto.text = String.format("%s %s", noSocio.nombre, noSocio.apellido)
                tvDni.text = noSocio.dni
                tvEmail.text = noSocio.mail
                tvTelefono.text = noSocio.telefono
                tvNacimiento.text = noSocio.fechaNacimiento

                // Iniciales del Avatar
                val iniN = noSocio.nombre.take(1).uppercase()
                val iniA = noSocio.apellido.take(1).uppercase()
                txtIniciales.text = String.format("%s%s", iniN, iniA)

                // Condición de ficha médica en tarjeta de datos
                if (noSocio.fichaMedica) {
                    tvActividad.text = "Pase Libre (Apto)"
                    tvActividad.setTextColor(getColor(R.color.titan_text_primary))
                } else {
                    tvActividad.text = "Falta Ficha Médica"
                    tvActividad.setTextColor(getColor(R.color.titan_red))
                }

                // --- NUEVO: Carga del historial desde la base de datos de pases diarios ---
                val (fechaPago, ultimaActividad) = helper.obtenerUltimoPagoNoSocio(dniRecibido!!)
                tvUltimoPago.text = fechaPago
                tvUltimaActividad.text = ultimaActividad

            } else {
                Toast.makeText(this, "Error al cargar datos del No Socio", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // ---------- SOLUCIÓN LÓGICA: CUADRO DE DIÁLOGO DE BAJA PERMANENTE ----------
    private fun mostrarDialogoConfirmarEliminar() {
        if (dniRecibido == null) return

        AlertDialog.Builder(this)
            .setTitle("¿Eliminar No Socio permanentemente?")
            .setMessage("Esta acción removerá por completo el registro de la persona de la base de datos y no se podrá deshacer. ¿Deseas continuar?")
            .setPositiveButton("Eliminar") { dialog, _ ->
                val filasAfectadas = helper.eliminarNoSocio(dniRecibido!!)

                if (filasAfectadas > 0) {
                    Toast.makeText(this, "No Socio eliminado correctamente", Toast.LENGTH_SHORT).show()

                    // Retornamos al menú principal reseteando la pila lúdica para refrescar las vistas globales
                    val intentVolver = Intent(this, MenuPrincipalActivity::class.java)
                    intentVolver.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intentVolver)
                    finish()
                } else {
                    Toast.makeText(this, "Error al intentar eliminar el registro", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show()
    }

    // Cuadro de diálogo interactivo para actualizar los datos personales
    private fun mostrarDialogoEditar() {
        if (dniRecibido == null) return
        val noSocio = helper.obtenerNoSocioPorDni(dniRecibido!!) ?: return

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Editar Datos No Socio")

        // Contenedor interno del diálogo
        val contenedorInputs = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(50, 20, 50, 20)
        }

        val inputNombre = EditText(this).apply { setText(noSocio.nombre); hint = "Nombre" }
        val inputApellido = EditText(this).apply { setText(noSocio.apellido); hint = "Apellido" }
        val inputTelefono = EditText(this).apply { setText(noSocio.telefono); hint = "Teléfono" }
        val inputEmail = EditText(this).apply { setText(noSocio.mail); hint = "Email" }

        val inputNacimiento = EditText(this).apply {
            setText(noSocio.fechaNacimiento)
            hint = "Fecha de Nacimiento (DD/MM/AAAA)"
            isFocusable = false
            isClickable = true
        }

        // Despliegue del DatePickerDialog para el input de nacimiento
        inputNacimiento.setOnClickListener {
            val cal = Calendar.getInstance()
            val fechaPartida = inputNacimiento.text.toString().split("/")
            if (fechaPartida.size == 3) {
                try {
                    cal.set(Calendar.DAY_OF_MONTH, fechaPartida[0].toInt())
                    cal.set(Calendar.MONTH, fechaPartida[1].toInt() - 1)
                    cal.set(Calendar.YEAR, fechaPartida[2].toInt())
                } catch (e: Exception) { e.printStackTrace() }
            }

            DatePickerDialog(this, { _, year, month, dayOfMonth ->
                val fechaSeleccionada = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year)
                inputNacimiento.setText(fechaSeleccionada)
            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
        }

        contenedorInputs.addView(inputNombre)
        contenedorInputs.addView(inputApellido)
        contenedorInputs.addView(inputTelefono)
        contenedorInputs.addView(inputEmail)
        contenedorInputs.addView(inputNacimiento)

        builder.setView(contenedorInputs)

        builder.setPositiveButton("Guardar") { dialog, _ ->
            val nom = inputNombre.text.toString().trim()
            val ape = inputApellido.text.toString().trim()
            val tel = inputTelefono.text.toString().trim()
            val mail = inputEmail.text.toString().trim()
            val nac = inputNacimiento.text.toString().trim()

            if (nom.isNotEmpty() && ape.isNotEmpty() && nac.isNotEmpty()) {
                // Ejecuta la actualización pasando los 6 parámetros correspondientes en SQLiteHelper
                val exito = helper.editarNoSocio(dniRecibido!!, nom, ape, tel, mail, nac)
                if (exito) {
                    Toast.makeText(this, "Cambios guardados con éxito", Toast.LENGTH_SHORT).show()
                    cargarDatosNoSocio() // Refresca las etiquetas de la pantalla automáticamente
                } else {
                    Toast.makeText(this, "Error al actualizar la base de datos", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Nombre, Apellido y Nacimiento son obligatorios", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancelar") { dialog, _ -> dialog.cancel() }
        builder.show()
    }

    // Refresco de ciclo de vida por si vuelve de pagar una actividad y debe actualizar el historial inferior
    override fun onResume() {
        super.onResume()
        cargarDatosNoSocio()
    }
}