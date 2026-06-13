package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.content.Intent
import android.os.Bundle
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

    // Listas para gestionar los filtros dinámicos
    private val listaCompletaUnificada = mutableListOf<WrapperMiembro>()
    private val listaFiltradaVisual = mutableListOf<WrapperMiembro>()
    private lateinit var adaptador: MiembroAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_miembros)

        // Inicializamos componentes
        helper = SQLiteHelper(this)
        rvMiembros = findViewById(R.id.rvMiembros)
        tvContadorMiembros = findViewById(R.id.tvContadorMiembros)

        val btnAgregarMiembro = findViewById<Button>(R.id.btnAgregarMiembro)
        val btnFiltroTodos = findViewById<Button>(R.id.btnFiltroTodos)
        val btnSocios = findViewById<Button>(R.id.btnSocios)
        val btnNoSocios = findViewById<Button>(R.id.btnNoSocios)
        val btnFiltroVencenHoy = findViewById<Button>(R.id.btnFiltroVencenHoy)

        // Botones de navegación inferior
        val navInicio = findViewById<LinearLayout>(R.id.nav_inicio)
        val navMiembros = findViewById<LinearLayout>(R.id.nav_miembros)
        val navCobrar = findViewById<LinearLayout>(R.id.nav_cobrar)
        val navMas = findViewById<LinearLayout>(R.id.nav_mas)

        // 1. CARGA INICIAL DE DATOS DESDE SQLITE
        cargarDatosDesdeBaseDeDatos()

        // 2. CONFIGURACIÓN DEL RECYCLERVIEW
        listaFiltradaVisual.addAll(listaCompletaUnificada) // Al principio mostramos todos
        adaptador = MiembroAdapter(listaFiltradaVisual)
        rvMiembros.layoutManager = LinearLayoutManager(this)
        rvMiembros.adapter = adaptador
        actualizarContador()

        // 3. LOGICA DE FILTRADO DE BOTONES
        btnFiltroTodos.setOnClickListener {
            listaFiltradaVisual.clear()
            listaFiltradaVisual.addAll(listaCompletaUnificada)
            adaptador.notifyDataSetChanged()
            actualizarContador()
        }

        btnSocios.setOnClickListener {
            listaFiltradaVisual.clear()
            listaFiltradaVisual.addAll(listaCompletaUnificada.filter { it.esSocio })
            adaptador.notifyDataSetChanged()
            actualizarContador()
        }

        btnNoSocios.setOnClickListener {
            listaFiltradaVisual.clear()
            listaFiltradaVisual.addAll(listaCompletaUnificada.filter { !it.esSocio })
            adaptador.notifyDataSetChanged()
            actualizarContador()
        }

        btnFiltroVencenHoy.setOnClickListener {
            listaFiltradaVisual.clear()
            // Filtrará los miembros que tengan la flag de deuda activa
            listaFiltradaVisual.addAll(listaCompletaUnificada.filter { it.deudorVencido })
            adaptador.notifyDataSetChanged()
            actualizarContador()
        }

        // 4. ACCIÓN AGREGAR MIEMBRO
        btnAgregarMiembro.setOnClickListener {
            val intentarAgregarMiembro = Intent(this, EleccionNuevoMiembroActivity::class.java)
            startActivity(intentarAgregarMiembro)
        }

        // 5. NAVEGACIÓN DEL FOOTER
        navInicio.setOnClickListener {
            val intentarInicio = Intent(this, MenuPrincipalActivity::class.java)
            startActivity(intentarInicio)
            finish()
        }
        navMiembros.setOnClickListener {
            // Ya estamos aquí, hacemos scroll al inicio de la lista
            rvMiembros.smoothScrollToPosition(0)
        }
        navCobrar.setOnClickListener {
            val intentarCobrar = Intent(this, CobrarCuotaActivity::class.java)
            startActivity(intentarCobrar)
            finish()
        }
        navMas.setOnClickListener {
            Toast.makeText(this, "EN PROGRESO...", Toast.LENGTH_SHORT).show()
        }
    }

    // Método que lee SQLite y mapea a nuestro objeto visual unificado
    private fun cargarDatosDesdeBaseDeDatos() {
        listaCompletaUnificada.clear()

        // Traemos todos los socios reales usando el método correcto en español
        val sociosBD = helper.obtenerSocios()
        for (s in sociosBD) {
            listaCompletaUnificada.add(
                WrapperMiembro(
                    nombre = s.nombre,
                    apellido = s.apellido,
                    dni = s.dni,
                    esSocio = true,
                    deudorVencido = false
                )
            )
        }

        // Traemos todos los no socios reales usando el método correcto en español
        val noSociosBD = helper.obtenerNoSocios()
        for (ns in noSociosBD) {
            listaCompletaUnificada.add(
                WrapperMiembro(
                    nombre = ns.nombre,
                    apellido = ns.apellido,
                    dni = ns.dni,
                    esSocio = false,
                    deudorVencido = false
                )
            )
        }
    }

    private fun actualizarContador() {
        tvContadorMiembros.text = "${listaFiltradaVisual.size} Miembros"
    }
}