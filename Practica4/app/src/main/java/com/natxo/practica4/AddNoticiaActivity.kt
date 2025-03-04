package com.natxo.practica4

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope

import com.natxo.practica4.database.entity.NoticiaEntity
import com.natxo.practica4.databinding.ActivityAddNoticiaBinding
import com.natxo.practica4.NoticiasApplication.Companion.db
import com.natxo.practica4.database.entity.UsuarioEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddNoticiaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoticiaBinding

    private  var user: UsuarioEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityAddNoticiaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getSerializableExtra("Usuario") as? UsuarioEntity

        noticiaInsert()

    }

    private fun noticiaInsert(){
        binding.btnNew.setOnClickListener{
            val newNoticia = NoticiaEntity(

                titulo = binding.etTitulo.text.toString(),
                descripcion = binding.etResumen.text.toString(),
                fecha = binding.etFecha.text.toString(),
                imagenUrl = binding.etImagenPortada.text.toString(),
                noticiaUrl = binding.etEnlace.text.toString()
            )

            setNoticiaEntity(newNoticia)
            goToNoticias()
        }
    }

    private fun setNoticiaEntity(noticiaEntity: NoticiaEntity) {
        lifecycleScope.launch(Dispatchers.IO) {
                db
                .noticiaDao()
                .setNoticia(noticiaEntity)
        }
    }

    private fun goToNoticias(){
        intent = Intent(this, NoticiasActivity::class.java)
        intent.putExtra("Usuario", user)
        startActivity(intent)
    }
}