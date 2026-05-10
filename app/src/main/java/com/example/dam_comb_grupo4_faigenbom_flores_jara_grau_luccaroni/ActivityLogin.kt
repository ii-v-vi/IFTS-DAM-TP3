package com.example.dam_comb_grupo4_faigenbom_flores_jara_grau_luccaroni

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent


class ActivityLogin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val btnLogin = findViewById<TextView>(R.id.btnLogin)
        android.util.Log.d("LOGIN", "btnLogin es null? ${btnLogin == null}")

        btnLogin.setOnClickListener {
            val intentarLoguear = Intent(this, MenuPrincipalActivity::class.java)
            startActivity(intentarLoguear)
        }




    }
}