package com.natxo.practica4

import android.annotation.SuppressLint
import android.app.Application
import androidx.room.Room
import com.natxo.practica4.database.NoticiaDatabase
import com.natxo.practica4.database.entity.NoticiaEntity
import com.natxo.practica4.database.entity.UsuarioEntity
import com.natxo.practica4.sharedpreferences.Prefs
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class NoticiasApplication: Application() {

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var prefs: Prefs
        lateinit var db: NoticiaDatabase
    }


    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)

        db = Room.databaseBuilder(
            this,
            NoticiaDatabase::class.java,
            "noticias_db")
            .build()

        defaultData()
    }
    @OptIn(DelicateCoroutinesApi::class)
    private fun defaultData() {
        // Se usa GlobalScope porque esta operación de inserción inicial de datos
        // no está ligada a ningún componente específico de la UI y debe ejecutarse
        // independientemente del ciclo de vida de una Activity.
        // Usar lifecycleScope aquí podría hacer que la operación se cancele si
        // la Activity se destruye antes de completarse.
        GlobalScope.launch(Dispatchers.IO) {
            val noticiasObtenidas = db
                .noticiaDao()
                .getNoticias()
            if (noticiasObtenidas.size <= 0) {
                listOf(
                    NoticiaEntity(
        titulo = "Incendio en California",
        descripcion = "Un incendio que avanza sin control en Los Ángeles y que ya ha llegado hasta el cartel de Hollywood.",
        fecha = "10/01/2025",
        esFavorita = false,
        imagenUrl = "https://upload.wikimedia.org/wikipedia/commons/1/1a/PalisadesFire_fromDowntown.png",
        noticiaUrl = "https://www.antena3.com/noticias/sociedad/noticias-hoy-viernes-10-enero-2025_202501106780c357af217500014b8c99.html"
    ),
    NoticiaEntity(
        titulo = "Karla Souza pierde su casa en Los Ángeles a causa de los incendios",
        descripcion = "La actriz mexicana publicó una serie de videos en su cuenta de Instagram, acompañados de un mensaje en el que asegura que ella y su familia están bien.",
        fecha = "10/01/2025",
        esFavorita = false,
        imagenUrl = "https://imagenes.elpais.com/resizer/v2/FKBJG622JNB6XLM4VNXTUWAFNE.jpg?auth=83dabb9443c1fbaa8b1cec0d6c5dc4dd42ee6089d1b6a5e1be7a5218bd666650&width=1200",
        noticiaUrl = "https://elpais.com/mexico/2025-01-10/karla-souza-pierde-su-casa-en-los-angeles-a-causa-de-los-incendios.html"
    ),
    NoticiaEntity(
        titulo = "Dos detenidos por la muerte de un operario de limpieza",
        descripcion = "Los arrestados, acusados de delito de homicidio imprudente, son también trabajadores que se encontraban en el centro",
        fecha = "10/01/2025",
        esFavorita = false,
        imagenUrl = "https://imagenes.elpais.com/resizer/v2/ONASXR5ZLFHCTGBDVJC4TLXSOM.jpg?auth=4ce4330af69189a75f726d692bda46eb0fc87753386dd705b1a4bcac53239999&width=1200",
        noticiaUrl = "https://elpais.com/espana/comunidad-valenciana/2025-01-10/dos-detenidos-por-la-muerte-de-un-operario-de-limpieza-en-un-colegio-danado-por-la-dana-en-valencia.html"
    ),
    NoticiaEntity(
        titulo = "El Real Madrid completa su resurrección en Kaunas",
        descripcion = "El conjunto de Chus Mateo vence con solvencia al Zalgiris (64-83), su quinta victoria seguida en la Euroliga",
        fecha = "10/01/2025",
        esFavorita = false,
        imagenUrl = "https://imagenes.elpais.com/resizer/v2/7KKQELWWEJBA5GMUUEC5MRCLFE.jpg?auth=d3a5490d0fb7b6d36c1c7ae8fc703d066d983f3f129d37315c613205170bf484&width=1200",
        noticiaUrl = "https://elpais.com/deportes/baloncesto/2025-01-10/el-real-madrid-completa-su-resurreccion-en-kaunas.html"
    ),
    NoticiaEntity(
        titulo = "Atrapada entre el PRI y Morena, la colonia Cuarta Transformación no termina de transformarse",
        descripcion = "Los vecinos del barrio irregular, en Tultitlán, Estado de México, acuerdan una consulta popular tras arrancar los nuevos nombres de las calles, que homenajeaban a López Obrador por decisión de la alcaldesa",
        fecha = "10/01/2025",
        esFavorita = false,
        imagenUrl = "https://imagenes.elpais.com/resizer/v2/YVCCFVWHG5FPVAJ3O5KSXMYSBI.jpg?auth=1d2a56c377e253263a2b1a1c6fd0559b76e25729bb58c71bf9f3946d1c49d01f&width=1200",
        noticiaUrl = "https://elpais.com/mexico/2025-01-10/atrapada-entre-el-pri-y-morena-la-colonia-cuarta-transformacion-no-termina-de-transformarse.html"
    )
                ).forEach { noticia ->
                    db
                        .noticiaDao()
                        .setNoticia(noticia)
                }

            }
            val usuariosObtenidos = db
                .noticiaDao()
                .getNoticias()
            if (usuariosObtenidos.size <= 0) {
                listOf(
                    UsuarioEntity(email = "a", nombre = "a", contrasena = "a")
                ).forEach { e ->
                    db
                        .usuarioDao()
                        .setUsuario(e)
                }
            }
        }
    }
}
