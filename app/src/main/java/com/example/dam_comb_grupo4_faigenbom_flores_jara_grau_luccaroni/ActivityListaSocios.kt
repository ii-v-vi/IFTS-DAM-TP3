package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ActivityListaSocios : AppCompatActivity() {

    private lateinit var adapter: SocioAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_lista_socios)

        val helper = SQLiteHelper(this)
        RepositorioSocios.listaSocios.clear()
        RepositorioSocios.listaSocios.addAll(helper.obtenerSocios())

        val rvSocios =
            findViewById<RecyclerView>(R.id.rvSocios)

        val btnVolver = findViewById<LinearLayout>(R.id.llVolver)

        adapter =
            SocioAdapter(
                RepositorioSocios.listaSocios
            )

        rvSocios.layoutManager =
            LinearLayoutManager(this)

        rvSocios.adapter =
            adapter

        btnVolver.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        adapter.notifyDataSetChanged()
    }
}