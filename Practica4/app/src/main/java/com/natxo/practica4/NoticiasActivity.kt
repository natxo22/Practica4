package com.natxo.practica4

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.natxo.practica4.NoticiasApplication.Companion.db
import com.natxo.practica4.NoticiasApplication.Companion.prefs
import com.natxo.practica4.adapter.NoticiaAdapter
import com.natxo.practica4.database.entity.FavoritoEntity
import com.natxo.practica4.database.entity.NoticiaEntity
import com.natxo.practica4.database.entity.UsuarioEntity
import com.natxo.practica4.databinding.ActivityRecyclerBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoticiasActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRecyclerBinding

    private lateinit var layoutLineal: LinearLayoutManager
    private lateinit var adapterNoticias: NoticiaAdapter

    private  var user: UsuarioEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = intent.getSerializableExtra("Usuario") as? UsuarioEntity

        Log.i("aaa", user?.nombre.toString()) //Log para comprobar si habia usuarios
        initRecyclerView()
        setupBottomNavigation()

        closeSesion()
        goToAddNoticia()

        loadLastTitle()
    }

    private fun initRecyclerView() {

        adapterNoticias = NoticiaAdapter(
            mutableListOf(),
            { e -> favButtonAction(e) },
            { e -> editButtonAction(e) },
            { e -> deletButtonAction(e) }
        )

        layoutLineal = LinearLayoutManager(this)

        if (user != null){
            binding.username.text = user!!.nombre.toString()
        }

        getNoticias()

        binding.recycler.apply {
            setHasFixedSize(true)
            layoutManager = layoutLineal
            adapter = adapterNoticias
        }
    }

    private fun closeSesion(){
        binding.btn.setOnClickListener({
            prefs.clearAll()
            goToLogin()
        })
    }

    private fun goToAddNoticia(){
        binding.btnAdd.setOnClickListener({
            startActivity(Intent(this, AddNoticiaActivity::class.java))
        })
    }

    private fun goToLogin(){
        binding.btn.setOnClickListener({
            startActivity(Intent(this, MainActivity::class.java))
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

    private fun getNoticias() {
        lifecycleScope.launch(Dispatchers.IO) {
            val noticias =
                db
                .noticiaDao()
                .getNoticias()

            val favoritos = user?.let {
                    db
                    .favoritoDao()
                    .getFavoritos(it.id)
            }

            noticias.forEach { noticia ->
                if (favoritos != null) {
                    noticia.esFavorita = favoritos.any { it.noticiaId == noticia.id }
                }
            }

            withContext(Dispatchers.Main) {
                adapterNoticias.getNoticiaAdapter(noticias)
            }
        }
    }

    //Button actions
    private fun favButtonAction(noticiaEntity: NoticiaEntity) {
        noticiaEntity.esFavorita = !noticiaEntity.esFavorita
        adapterNoticias.updateNoticiaAdapter(noticiaEntity)
        lifecycleScope.launch(Dispatchers.IO) {
            // Esto asignará 1 en caso de que user sea null. (No debería)
            //user?.id ?: 1
            val favoritoEntity = FavoritoEntity(user?.id ?: 1, noticiaEntity.id)
            if (noticiaEntity.esFavorita) {
                db
                    .favoritoDao()
                    .setFavoritos(favoritoEntity)
            } else {
                db
                    .favoritoDao()
                    .deleteFavorito(favoritoEntity)
            }
            db
                .noticiaDao()
                .updateNoticia(noticiaEntity)
        }
    }

    private fun editButtonAction(noticiaEntity: NoticiaEntity) {
        intent = Intent(this, EditNoticiaActivity::class.java)
        intent.putExtra("Noticia", noticiaEntity)
        startActivity(intent)
    }

    private fun deletButtonAction(noticiaEntity: NoticiaEntity){
        adapterNoticias.deleteNoticiaAdapter(noticiaEntity)
        lifecycleScope.launch(Dispatchers.IO) {
            db
                .noticiaDao()
                .deleteNoticia(noticiaEntity)
        }
    }

    //Bottom nav
    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    getNoticias()
                    true
                }
                R.id.fav -> {
                    showFavorites()
                    true
                }
                else -> false
            }
        }
    }

    private fun showFavorites() {
        lifecycleScope.launch(Dispatchers.IO) {

            val favoritos = user?.let {
                db.favoritoDao().getFavoritos(it.id)
            }

            val noticiasFavoritas = mutableListOf<NoticiaEntity>()
            favoritos?.forEach { favorito ->
                val noticia = db.noticiaDao().getNoticias().find { it.id == favorito.noticiaId }
                if (noticia != null) {
                    noticiasFavoritas.add(noticia)
                }
            }

            withContext(Dispatchers.Main) {
                adapterNoticias.getNoticiaAdapter(noticiasFavoritas)
            }
        }
    }
}