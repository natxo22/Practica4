package com.natxo.practica4.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.natxo.practica4.database.entity.FavoritoEntity

@Dao
interface FavoritoDao {
    @Query("SELECT * FROM FavoritoEntity WHERE usuarioId = :userId")
    suspend fun getFavoritos(userId: Long): MutableList<FavoritoEntity>
    @Insert
    suspend fun setFavoritos(favoritoEntity: FavoritoEntity)
    @Delete
    suspend fun deleteFavorito(favoritoEntity: FavoritoEntity)
}