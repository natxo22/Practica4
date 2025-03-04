package com.natxo.practica4

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.natxo.practica4.NoticiasApplication.Companion.db
import com.natxo.practica4.NoticiasApplication.Companion.prefs
import com.natxo.practica4.database.entity.UsuarioEntity
import com.natxo.practica4.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLogin()
        register()

        checkPreferences()
    }

    private fun register() {
        binding.btnRegister.setOnClickListener { goToRegister() }
    }

    private fun initLogin() {
        binding.btnLogin.setOnClickListener { userConfirmation() }
    }

    private fun userConfirmation() {
        if (binding.etUsername.text.toString().isNotEmpty() &&
            binding.etPassword.text.toString().isNotEmpty()
        ) {

            lifecycleScope.launch {
                val user: UsuarioEntity? = getUserEntity(
                    binding.etUsername.text.toString(),
                    binding.etPassword.text.toString()
                )
                if (user != null) {
                    savePreferences(user)
                    goToNoticias(user)
                } else {
                    val toast =
                        Toast.makeText(
                            applicationContext,
                            R.string.login_missmatch,
                            Toast.LENGTH_SHORT
                        )
                    toast.show()
                }
            }

        } else {
            val toast =
                Toast.makeText(
                    applicationContext,
                    "Rellene los campos",
                    Toast.LENGTH_SHORT
                )
            toast.show()
        }
    }

    @SuppressLint("UnsafeIntentLaunch")
    private fun goToNoticias(user: UsuarioEntity?) {
        intent = Intent(this, NoticiasActivity::class.java)
        intent.putExtra("Usuario", user)
        startActivity(intent)
    }

    private fun goToRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    private fun savePreferences(user: UsuarioEntity) {
        prefs.setName(user.email.toString())
        prefs.setPassword(user.contrasena.toString())
    }

    private fun checkPreferences(){
        lifecycleScope.launch {
            if (prefs.getName().isNotEmpty() &&
                prefs.getPassword().isNotEmpty()
            ) {
                val user: UsuarioEntity? = getUserEntity(
                    prefs.getName(),
                    prefs.getPassword()
                )
                goToNoticias(user)
            }
        }
    }

    private suspend fun getUserEntity(email: String?, contrasena: String?): UsuarioEntity? {
        if (email != null && contrasena != null) {
            return withContext(Dispatchers.IO) {
                db.usuarioDao().getUsuario(email.trim(), contrasena)
            }
        }
        return null
    }
}