package com.natxo.practica4.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "UsuarioEntity")
data class UsuarioEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val email: String? = "",
    val nombre: String? = "",
    val contrasena: String? = ""
) : Serializable
