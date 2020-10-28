package com.example.linkshare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    lateinit var editTextName: EditText
    lateinit var btnCrearDatos: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextName = findViewById(R.id.editTitulo)
        btnCrearDatos = findViewById(R.id.btnCrearDatos)

        btnCrearDatos.setOnClickListener{
            saveFirebaseData()
        }
    }

    private fun saveFirebaseData(){
        var tituloText = editTextName.text.toString().trim()

        if(tituloText.isEmpty()){
            tituloText = "No hay titulo"
        }


        val ref = FirebaseDatabase.getInstance().getReference("titulos")
        val tituloId = ref.push().key
        val titulo = Titulo(tituloId, tituloText)

        if (tituloId != null) {
            ref.child(tituloId).setValue(titulo).addOnCompleteListener{
                Toast.makeText(applicationContext, "Titulo saved", Toast.LENGTH_SHORT)
            }
        }

        return
    }
}