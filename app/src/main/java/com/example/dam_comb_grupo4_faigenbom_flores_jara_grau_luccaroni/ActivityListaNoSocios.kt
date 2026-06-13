package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ActivityListaNoSocios : AppCompatActivity() {

    private lateinit var adapter: NoSocioAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_no_socios)

        val rvNoSocios = findViewById<RecyclerView>(R.id.rvNoSocios)
        val btnVolver = findViewById<LinearLayout>(R.id.llVolver)

        val helper = SQLiteHelper(this)
        
        // Sincronizar memoria con base de datos
        RepositorioNoSocios.listaNoSocios.clear()
        RepositorioNoSocios.listaNoSocios.addAll(helper.obtenerNoSocios())

        adapter = NoSocioAdapter(RepositorioNoSocios.listaNoSocios)
        rvNoSocios.layoutManager = LinearLayoutManager(this)
        rvNoSocios.adapter = adapter

        btnVolver.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        adapter.notifyDataSetChanged()
    }
}