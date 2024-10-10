package com.hugo.playas.ui

import Playa
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hugo.playas.databinding.ListItemBinding

class PlayaHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val itemBinding = ListItemBinding.bind(itemView)

    //Cargamos los datos en el ViewHolder
    fun bind(playa: Playa,  onClickListener:(Playa)->Unit) {

        //Dominio donde se encuentran las imágenes
        val base= "https://www.turismoasturias.es"

        //Separamos las diferentes URLs que obtenemos
        val urlsArray = playa.Slide.split(",")

        itemBinding.tvNombre.text = playa.Nombre.toString()
        itemBinding.tvConcejo.text=playa.Concejo.toString()
        itemBinding.tvZona.text=playa.Zona.toString()

        //Se utiliza la bilblioteca Glide para cargar la foto
        Glide.with(itemBinding.imageView.context).load(base+urlsArray.get(0)).into(itemBinding.imageView)

        //Listener para identificar cuando se pulsa en el recyclerView
        itemView.setOnClickListener{(onClickListener(playa))}
    }
}
