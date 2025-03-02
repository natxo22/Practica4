package com.natxo.practica4

import com.natxo.practica4.database.entity.NoticiaEntity

interface OnClickListener {
    fun alHacerClic(noticiaEntity: NoticiaEntity)

    fun favButtonAction(noticiaEntity: NoticiaEntity)

    fun alEliminar(noticiaEntity: NoticiaEntity)
}
