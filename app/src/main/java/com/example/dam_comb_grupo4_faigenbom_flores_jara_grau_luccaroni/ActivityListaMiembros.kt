package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ActivityListaMiembros : AppCompatActivity() {

    private lateinit var helper: SQLiteHelper
    private lateinit var rvMiembros: RecyclerView
    private lateinit var tvContadorMiembros: TextView
    private lateinit var etBuscar: EditText

    private val listaCompletaUnificada = mutableListOf<WrapperMiembro>()
    private val listaFiltradaVisual = mutableListOf<WrapperMiembro>()
    private lateinit var adaptador: MiembroAdapter

    private var filtroEstadoActual: String = "TODOS"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_miembros)

        helper = SQLiteHelper(this)
        rvMiembros = findViewById(R.id.rvMiembros)
        tvContadorMiembros = findViewById(R.id.tvContadorMiembros)
        etBuscar = findViewById(R.id.etBuscar)

        val btnAgregarMiembro = findViewById<Button>(R.id.btnAgregarMiembro)
        val btnFiltroTodos = findViewById<Button>(R.id.btnFiltroTodos)
        val btnSocios = findViewById<Button>(R.id.btnSocios)
        val btnNoSocios = findViewById<Button>(R.id.btnNoSocios)
        val btnFiltroVencenHoy = findViewById<Button>(R.id.btnFiltroVencenHoy)

        val navInicio = findViewById<LinearLayout>(R.id.nav_inicio)
        val navMiembros = findViewById<LinearLayout>(R.id.nav_miembros)
        val navMas = findViewById<LinearLayout>(R.id.nav_mas)


        val modoFiltroHome = intent.getStringExtra("FILTRO_MODO")
        if (modoFiltroHome == "FILTRO_VENCEN_HOY") {
            filtroEstadoActual = "VENCENHOY"
        }

        adaptador = MiembroAdapter(listaFiltradaVisual)
        rvMiembros.layoutManager = LinearLayoutManager(this)
        rvMiembros.adapter = adaptador

        btnFiltroTodos.setOnClickListener {
            filtroEstadoActual = "TODOS"
            aplicarBusquedaYFiltro(etBuscar.text.toString())
        }

        btnSocios.setOnClickListener {
            filtroEstadoActual = "SOCIOS"
            aplicarBusquedaYFiltro(etBuscar.text.toString())
        }

        btnNoSocios.setOnClickListener {
            filtroEstadoActual = "NOSOCIOS"
            aplicarBusquedaYFiltro(etBuscar.text.toString())
        }

        btnFiltroVencenHoy.setOnClickListener {
            filtroEstadoActual = "VENCENHOY"
            aplicarBusquedaYFiltro(etBuscar.text.toString())
        }

        etBuscar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                aplicarBusquedaYFiltro(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        btnAgregarMiembro.setOnClickListener {
            val intentarAgregarMiembro = Intent(this, EleccionNuevoMiembroActivity::class.java)
            startActivity(intentarAgregarMiembro)
        }

        navInicio.setOnClickListener {
            val intentarInicio = Intent(this, MenuPrincipalActivity::class.java)
            startActivity(intentarInicio)
            onBackPressedDispatcher.onBackPressed()
            finish()
        }
        navMiembros.setOnClickListener {
            rvMiembros.smoothScrollToPosition(0)
        }
        navMas.setOnClickListener {
            Toast.makeText(this, "EN PROGRESO...", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()

        cargarDatosDesdeBaseDeDatos()

        aplicarBusquedaYFiltro(etBuscar.text.toString())
    }

    private fun cargarDatosDesdeBaseDeDatos() {
        listaCompletaUnificada.clear()

        val sociosUnificados = helper.obtenerSociosCompleto()
        listaCompletaUnificada.addAll(sociosUnificados)

        val noSociosBD = helper.obtenerNoSocios()
        for (ns in noSociosBD) {
            listaCompletaUnificada.add(
                WrapperMiembro(
                    nombre = ns.nombre,
                    apellido = ns.apellido,
                    dni = ns.dni,
                    esSocio = false,
                    deudorVencido = false,
                    fechaVencimiento = null
                )
            )
        }
    }

    private fun aplicarBusquedaYFiltro(textoBusqueda: String) {
        listaFiltradaVisual.clear()

        val listaPorCategoria = when (filtroEstadoActual) {
            "SOCIOS" -> listaCompletaUnificada.filter { it.esSocio }
            "NOSOCIOS" -> listaCompletaUnificada.filter { !it.esSocio }
            "VENCENHOY" -> listaCompletaUnificada.filter { it.deudorVencido }
            else -> listaCompletaUnificada
        }

        if (textoBusqueda.isEmpty()) {
            listaFiltradaVisual.addAll(listaPorCategoria)
        } else {
            val query = textoBusqueda.lowercase().trim()
            val filtradosPorTexto = listaPorCategoria.filter { miembro ->
                miembro.nombre.lowercase().contains(query) ||
                        miembro.apellido.lowercase().contains(query) ||
                        miembro.dni.contains(query)
            }
            listaFiltradaVisual.addAll(filtradosPorTexto)
        }

        adaptador.notifyDataSetChanged()
        actualizarContador()
    }

    private fun actualizarContador() {
        tvContadorMiembros.text = "${listaFiltradaVisual.size} Miembros"
    }
}