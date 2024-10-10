package com.hugo.playas.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.hugo.playas.databinding.FragmentDetallesBinding
import com.hugo.playas.domain.PlayasViewModel


class DetallesFragment : Fragment() {

    //Para la vinculación de vistas
    private lateinit var _binding: FragmentDetallesBinding
    private val binding get() = _binding!!

    //Declarar el ViewModel
    private lateinit var playasViewModel: PlayasViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Instanciamos el mismo VM que la actividad
        playasViewModel = ViewModelProvider(requireActivity()).get(PlayasViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetallesBinding.inflate(inflater, container, false)
        //llamada para actualizar las vistas
        actualizarVista()
        return binding.root
    }

    //Función que se encargara de mostrar los datos de la playa en el layout
    fun actualizarVista(){

        //Bindear la información simple en las etiquetas
        binding.tvNombre.text= playasViewModel.playaSeleccionada?.Nombre
        binding.tvConcejo.text= playasViewModel.playaSeleccionada?.Concejo + " - " + playasViewModel.playaSeleccionada?.Zona
        binding.tvInfo.text= playasViewModel.playaSeleccionada?.InformacionTexto
        binding.tvTipo.text= playasViewModel.playaSeleccionada?.TipoDePlaya
        binding.tvAcceso.text= playasViewModel.playaSeleccionada?.Accesos
        binding.tvLongitud.text= playasViewModel.playaSeleccionada?.Longitud

        //Para mostrar los servicios en forma de lista
        var arrayServicios = playasViewModel.playaSeleccionada?.Servicios!!.split(",").toTypedArray()
        val texto = arrayServicios.joinToString("\n")
        binding.tvServicios.text=texto

        //Para mostrar la imágen
        //Dominio donde se encuentran las imágenes
        val base= "https://www.turismoasturias.es"
        //Separamos las diferentes URLs que obtenemos
        val urlsArray = playasViewModel.playaSeleccionada?.Slide?.split(",")

        if (urlsArray != null) {
            Glide.with(binding.imgPlaya.context).load(base+urlsArray.get(0)).into(binding.imgPlaya)
        }

    }
}