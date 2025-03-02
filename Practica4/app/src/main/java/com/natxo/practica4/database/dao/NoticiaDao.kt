package com.natxo.practica4.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.natxo.practica4.database.entity.NoticiaEntity

@Dao
interface NoticiaDao {

    @Query("SELECT * FROM NoticiaEntity")
    suspend fun getNoticias(): MutableList<NoticiaEntity>

    //@Query("SELECT n.id FROM NoticiaEntity n INNER JOIN FavoritoEntity f ON n.id = f.noticiaId WHERE f.usuarioId = :usuarioId")
//    suspend fun getNoticiasFavoritas(usuarioId: Long): List<NoticiaEntity>

    @Insert
    suspend fun setNoticia(noticiaEntity: NoticiaEntity)

    @Update
    suspend fun updateNoticia(noticiaEntity: NoticiaEntity)

    @Delete
    suspend fun deleteNoticia(noticiaEntity: NoticiaEntity)

}
