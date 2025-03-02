package com.natxo.practica4.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.natxo.practica4.R
import com.natxo.practica4.database.entity.NoticiaEntity
import com.natxo.practica4.databinding.ItemNoticiaBinding

class NoticiaViewHolder(view: View):RecyclerView.ViewHolder(view){

    val binding = ItemNoticiaBinding.bind(view)

    fun render(noticiaModel: NoticiaEntity){

        Glide.with(binding.image.context)
            .load(noticiaModel.imagenUrl)
            .error(R.drawable.ic_launcher_background)
            .into(binding.image)

        binding.titulo.text = noticiaModel.titulo
        binding.description.text = noticiaModel.descripcion
        binding.fecha.text = noticiaModel.fecha

    }
}