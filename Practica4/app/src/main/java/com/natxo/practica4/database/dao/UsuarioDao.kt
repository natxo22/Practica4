package com.natxo.practica4.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.natxo.practica4.database.entity.UsuarioEntity

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM UsuarioEntity WHERE email = :email AND contrasena = :contrasena")
    suspend fun getUsuario(email: String, contrasena: String): UsuarioEntity?

    @Query("SELECT EXISTS(SELECT email FROM UsuarioEntity WHERE email = :email)")
    suspend fun getExistEmailBoolean(email: String): Boolean

    @Insert
    suspend fun setUsuario(usuarioEntity: UsuarioEntity)

    @Update
    suspend fun updateUsuario(usuarioEntity: UsuarioEntity)

    @Delete
    suspend fun deleteUsuario(usuarioEntity: UsuarioEntity)
}
