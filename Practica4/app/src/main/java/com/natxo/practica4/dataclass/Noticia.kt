package com.natxo.practica4.dataclass

data class Noticia(
    val id: Int,
    val titulo: String,
    val resumen: String,
    val fecha: String,
    val imagenPortada: String,
    val enlace: String
)