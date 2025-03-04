package com.natxo.practica4

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.natxo.practica4.NoticiasApplication.Companion.db
import com.natxo.practica4.database.entity.NoticiaEntity
import com.natxo.practica4.database.entity.UsuarioEntity
import com.natxo.practica4.databinding.ActivityEditNoticiaBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditNoticiaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditNoticiaBinding

    private  var user: UsuarioEntity? = null
    private  var noticiaEntity: NoticiaEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityEditNoticiaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noticiaEntity = intent.getSerializableExtra("Noticia") as NoticiaEntity
        user = intent.getSerializableExtra("Usuario") as? UsuarioEntity

        initEdit()
        updateNoticiaEntity()
    }

    private fun initEdit() {

        binding.etTitulo.setText(noticiaEntity?.titulo)
        binding.etResumen.setText(noticiaEntity?.descripcion)
        binding.etFecha.setText(noticiaEntity?.fecha)
        binding.etImagenPortada.setText(noticiaEntity?.imagenUrl)
        binding.etEnlace.setText(noticiaEntity?.noticiaUrl)
    }

    private fun updateNoticiaEntity() {
        binding.btn.setOnClickListener{
            val updateNoticia = NoticiaEntity(
                id = noticiaEntity!!.id,
                titulo = binding.etTitulo.text.toString(),
                descripcion = binding.etResumen.text.toString(),
                fecha = binding.etFecha.text.toString(),
                esFavorita = noticiaEntity!!.esFavorita,
                imagenUrl = binding.etImagenPortada.text.toString(),
                noticiaUrl = binding.etEnlace.text.toString()
            )

            lifecycleScope.launch(Dispatchers.IO) {
                db
                    .noticiaDao()
                    .updateNoticia(updateNoticia)
            }
            goToNoticias()
        }
    }

    private fun goToNoticias(){
        intent = Intent(this, NoticiasActivity::class.java)
        intent.putExtra("Usuario", user)
        startActivity(intent)
    }
}