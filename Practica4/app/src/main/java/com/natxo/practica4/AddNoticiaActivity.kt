package com.natxo.practica4

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope

import com.natxo.practica4.database.entity.NoticiaEntity
import com.natxo.practica4.databinding.ActivityAddNoticiaBinding
import com.natxo.practica4.NoticiasApplication.Companion.db
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddNoticiaActivity : AppCompatActivity() {


    private lateinit var binding: ActivityAddNoticiaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAddNoticiaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noticiaInsert()

    }

    private fun noticiaInsert(){
        binding.btnNew.setOnClickListener({
            val newNoticia = NoticiaEntity(

                titulo = binding.etTitulo.toString(),
                descripcion = binding.etResumen.toString(),
                fecha = binding.etFecha.toString(),
                imagenUrl = binding.etImagenPortada.toString(),
                noticiaUrl = binding.etEnlace.toString()
            )

            setNoticiaEntity(newNoticia)
        })
    }

    private fun setNoticiaEntity(noticiaEntity: NoticiaEntity) {
        lifecycleScope.launch(Dispatchers.IO) {
                db
                .noticiaDao()
                .setNoticia(noticiaEntity)
        }
    }
}