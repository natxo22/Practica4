package com.natxo.practica4

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.natxo.practica4.NoticiasApplication.Companion.prefs
import com.natxo.practica4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLogin()
        register()

        //checkPreferences()
    }

    private fun register() {
        binding.btnRegister.setOnClickListener { goToRegister()}
    }

    private fun initLogin() {
        binding.btnLogin.setOnClickListener { userConfirmation()}
    }

    private fun userConfirmation() {
        if(binding.etUsername.text.toString().isNotEmpty() &&
            binding.etPassword.text.toString().isNotEmpty()){

            goToNoticias()
            savePreferences(
                binding.etUsername.text.toString(),
                binding.etPassword.text.toString())

        }else{
            val toast =
                Toast.makeText(
                    applicationContext,
                    R.string.login_missmatch,
                    Toast.LENGTH_SHORT
                )
            toast.show()
        }
    }

    private fun goToNoticias(){
        startActivity(Intent(this, NoticiasActivity::class.java))
    }

    private fun goToRegister(){
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    private fun savePreferences(username: String, password: String){
        prefs.setName(username)
        prefs.setPassword(password)
    }

    private fun checkPreferences(){
        if(prefs.getName().isNotEmpty()  &&
            prefs.getPassword().isNotEmpty()
            ){

            goToNoticias()
        }
    }
}