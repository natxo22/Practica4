package com.natxo.practica4.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.natxo.practica4.Noticia
import com.natxo.practica4.NoticiasApplication.Companion.prefs
import com.natxo.practica4.R

class NoticiaAdapter(
    private val noticiaList : List<Noticia>
) : RecyclerView.Adapter<NoticiaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticiaViewHolder {
        val layoutInflater = LayoutInflater.from (parent.context)
        return NoticiaViewHolder(layoutInflater.inflate(R.layout.item_noticia, parent, false))
    }

    override fun getItemCount(): Int {
        return noticiaList.size
    }

    override fun onBindViewHolder(holder: NoticiaViewHolder, position: Int) {
        val item = noticiaList[position]
        holder.render(item)

        holder.binding.card.setOnClickListener{
            //Cargar imagenes desde los items del adaptador
            // (en mi windows 10 daba pantallazo azul, en el ordenador de clase funciona perfectamente)
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.enlace))
            holder.itemView.context.startActivity(intent)

            prefs.setLastClickedTitle(item.titulo)

        }
    }

}