package com.natxo.practica4

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope

import com.natxo.practica4.NoticiasApplication.Companion.db
import com.natxo.practica4.NoticiasApplication.Companion.prefs
import com.natxo.practica4.databinding.ActivityRegisterBinding
import com.natxo.practica4.database.entity.UsuarioEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        newUser()
    }

    private fun newUser(){
        binding.btnRegister.setOnClickListener {
            val nombre = binding.etUsername.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val contrasena = binding.etPassword.text.toString().trim()

            if (nombre.isEmpty() || email.isEmpty() || contrasena.isEmpty()) {
                val toast =
                    Toast.makeText(
                        applicationContext,
                        "Por favor, complete todos los campos",
                        Toast.LENGTH_SHORT
                    )
                toast.show()

            }

            lifecycleScope.launch(Dispatchers.IO) {
                val exist = db.usuarioDao().getExistEmailBoolean(email)
                withContext(Dispatchers.Main) {
                    if (!exist) {
                        getUserEntity(email, nombre, contrasena)
                        goToLogin()
                    } else {
                        val toast =
                            Toast.makeText(
                                applicationContext,
                                "Email en uso",
                                Toast.LENGTH_SHORT
                            )
                        toast.show()
                    }
                }
            }
        }
    }

    private fun goToLogin(){
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun getUserEntity(email: String, nombre: String, contrasena: String) {
        val user = UsuarioEntity(email = email, nombre = nombre, contrasena = contrasena)
        lifecycleScope.launch(Dispatchers.IO) {
            db.usuarioDao().setUsuario(user)
        }
    }
}