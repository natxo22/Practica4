package com.natxo.practica4.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE

@Entity(
    tableName = "FavoritoEntity",
    primaryKeys = ["usuarioId", "noticiaId"],
    foreignKeys = [
        ForeignKey(entity = UsuarioEntity::class, parentColumns = ["id"], childColumns = ["usuarioId"],
                onDelete = CASCADE),
        ForeignKey(entity = NoticiaEntity::class, parentColumns = ["id"], childColumns = ["noticiaId"],
                onDelete = CASCADE)
    ]
)
data class FavoritoEntity(
    val usuarioId: Long,
    val noticiaId: Long
)