package com.hugo.playas.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.hugo.playas.databinding.FragmentFiltroBinding
import com.hugo.playas.domain.FiltroViewModel

class FragmentFiltro : Fragment() {

    //Para la vinculación de vistas
    private lateinit var _binding: FragmentFiltroBinding
    private val binding get() = _binding!!
    var onActionListener: OnActionListener? = null

    private lateinit var filtrosViewModel: FiltroViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Instanciamos el mismo VM que la actividad
        filtrosViewModel = ViewModelProvider(requireActivity()).get(FiltroViewModel::class.java)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFiltroBinding.inflate(inflater, container, false)

        //Listeners para saber cuando hay cambio y modificar el viewmodel
        binding.zona.setOnCheckedChangeListener { group, checkedId ->
            if(binding.todasZonas.isChecked){
                filtrosViewModel.zonaSeleccionada=0
            }

            if(binding.zonaOriente.isChecked){
                filtrosViewModel.zonaSeleccionada=1
            }

            if(binding.zonaCentro.isChecked){
                filtrosViewModel.zonaSeleccionada=2
            }
            if(binding.zonaOccidente.isChecked){
                filtrosViewModel.zonaSeleccionada=3
            }

            onActionListener?.buscar()

        }

        binding.banderaAzul.setOnCheckedChangeListener { buttonView, isChecked ->
            filtrosViewModel.bandera = isChecked
            onActionListener?.buscar()
        }






        //Recuperamos la vista segun los datos del viewmodel
        //Si ya hay una zona seleccionada marcarla
        if(filtrosViewModel.zonaSeleccionada!=-1) {
            if(filtrosViewModel.zonaSeleccionada==0){
                binding.todasZonas.setChecked(true)
            }
            if(filtrosViewModel.zonaSeleccionada==1){
                binding.zonaOriente.setChecked(true)
            }
            if(filtrosViewModel.zonaSeleccionada==2){
                binding.zonaCentro.setChecked(true)
            }
            if(filtrosViewModel.zonaSeleccionada==3){
                binding.zonaOccidente.setChecked(true)
            }
        }


        //Si estaba seleccionada la bandera azul lo marcamos
        if(filtrosViewModel.bandera==true) {
            binding.banderaAzul.setChecked(true)
        }
        return binding.root
    }

    interface OnActionListener {
        fun buscar()
    }

}