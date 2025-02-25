package com.natxo.practica4.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.natxo.practica4.Noticia
import com.natxo.practica4.databinding.ItemNoticiaBinding

class NoticiaViewHolder(view: View):RecyclerView.ViewHolder(view){

    val binding = ItemNoticiaBinding.bind(view)

    fun render(noticiaModel: Noticia){

        Glide.with(binding.image.context).load(noticiaModel.imagenPortada).into(binding.image)
        binding.titulo.text = noticiaModel.titulo
        binding.description.text = noticiaModel.resumen
        binding.fecha.text = noticiaModel.fecha

    }
}