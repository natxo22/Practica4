package com.natxo.practica4.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.natxo.practica4.entity.NoticiaEntity

@Dao
interface NoticiaDao {
    @Query("SELECT * FROM NoticiaEntity")
    suspend fun obtenerNoticias(): MutableList<NoticiaEntity>
    @Insert
    suspend fun agregarNoticia(noticiaEntity: NoticiaEntity)
    @Update
    suspend fun actualizarNoticia(noticiaEntity: NoticiaEntity)
    @Delete
    suspend fun borrarNoticia(noticiaEntity: NoticiaEntity)

//    @Query("SELECT n.id FROM NoticiaEntity n INNER JOIN FavoritoEntity f ON n.id = f.noticiaId WHERE f.usuarioId = :usuarioId")
//    suspend fun obtenerNoticiasFavoritas(usuarioId: Long): List<NoticiaEntity>


}
