package com.natxo.practica4.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.natxo.practica4.entity.UsuarioEntity

@Dao
interface UsuarioDao {
    @Query("SELECT * FROM UsuarioEntity WHERE email = :email AND contrasena = :contrasena")
    suspend fun obtenerUsuario(email: String, contrasena: String): UsuarioEntity?

    @Query("SELECT id FROM UsuarioEntity WHERE id = :id")
    suspend fun obtenerIds(id: Int): List<Int>

    @Query("SELECT EXISTS(SELECT email FROM UsuarioEntity WHERE email = :email)")
    suspend fun obtenerEmail(email: String): Boolean

    @Insert
    suspend fun agregarUsuario(usuarioEntity: UsuarioEntity)

    @Update
    suspend fun actualizarUsuario(usuarioEntity: UsuarioEntity)

    @Delete
    suspend fun borrarUsuario(usuarioEntity: UsuarioEntity)
}
