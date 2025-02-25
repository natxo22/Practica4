package com.natxo.practica4.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.natxo.practica4.entity.FavoritoEntity

@Dao
interface FavoritoDao {
    @Query("SELECT * FROM FavoritoEntity WHERE usuarioId = :userId")
    suspend fun obtenerFavoritos(userId: Long): MutableList<FavoritoEntity>
    @Insert
    suspend fun meterFavoritos(favoritoEntity: FavoritoEntity)
    @Delete
    suspend fun borrarFavorito(favoritoEntity: FavoritoEntity)
}