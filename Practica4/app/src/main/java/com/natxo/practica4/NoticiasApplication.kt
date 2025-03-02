package com.natxo.practica4

import android.annotation.SuppressLint
import android.app.Application
import androidx.room.Room
import com.natxo.practica4.database.NoticiaDatabase
import com.natxo.practica4.database.entity.NoticiaEntity
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


    }
}