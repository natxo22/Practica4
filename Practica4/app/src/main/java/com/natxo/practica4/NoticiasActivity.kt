package com.natxo.practica4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.natxo.practica4.NoticiasApplication.Companion.prefs
import com.natxo.practica4.adapter.NoticiaAdapter
import com.natxo.practica4.databinding.ActivityRecyclerBinding

class NoticiasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecyclerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()

        closeSesion()
        addNoticia()

        loadLastTitle()
    }

    private fun initRecyclerView() {

        binding.recycler.layoutManager = LinearLayoutManager(this)
        binding.recycler.adapter = NoticiaAdapter(NoticiaProvider.noticiasList)
    }

    private fun closeSesion(){
        binding.btn.setOnClickListener({
            prefs.clearAll()
            onBackPressed()
        })
    }

    private fun addNoticia(){
        binding.btnAdd.setOnClickListener({
            startActivity(Intent(this, AddNoticiaActivity::class.java))
        })
    }

    private fun loadLastTitle(){
        if(prefs.getLastClickedTitle().isNotEmpty()){
            val toast =
                Toast.makeText(
                    applicationContext,
                    prefs.getLastClickedTitle(),
                    Toast.LENGTH_SHORT
                )
            toast.show()
        }else{
            val toast =
                Toast.makeText(
                    applicationContext,
                    "Bienvenido",
                    Toast.LENGTH_SHORT
                )
            toast.show()
        }
    }
}