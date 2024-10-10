package com.hugo.playas.fragments

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.networkapp.state.StopsUIState

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.hugo.playas.R
import com.hugo.playas.domain.PlayasViewModel

class Mapa : Fragment() {

    //Declarar el ViewModel
    private lateinit var playasViewModel: PlayasViewModel

    private val callback = OnMapReadyCallback { googleMap ->

        //Establecemos el centro de Asturias para cargar el mapa
        val asturias = LatLng(43.3602900, -5.8447600)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(asturias,8.0f))

        //Obtenemos la lista con las playas
       playasViewModel.stopsUIStateObservable.observe(viewLifecycleOwner) { result ->
            when (result) {
                is StopsUIState.Success -> {
                    Log.d("DEBUG", "Exito al traer los datos")

                    val listaPlayas = result.playa
                    for(playa in listaPlayas ){
                        Log.d("DEBUG", "Coordenada: "+playa.Coordenadas)

                        //Eliminamos los espacios ya que hay coordenadas que vienen con ellos
                        val cadenaSinEspacios = playa.Coordenadas.replace("\\s".toRegex(), "")
                        //Separamos longitud de latitud
                        val partes =cadenaSinEspacios.split(",")

                        //Comprobamos que tenemos dos componentes y añadimos la playa al mapa
                        if(partes.size==2) {
                            val latitud = partes[0].toDouble()
                            val longitud = partes[1].toDouble()

                            val loc = LatLng(latitud, longitud)
                            googleMap.addMarker(MarkerOptions().position(loc).title(playa.Nombre))
                        }
                    }

                }
                is StopsUIState.Error -> {
                    // Mostrar un SnackBar con el mensaje de error
                    Log.d("DEBUG", "Error al traer los datos")
                }
                else -> {
                    Log.d("DEBUG", "Cargando")
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Instanciamos el mismo VM que la actividad
        playasViewModel = ViewModelProvider(requireActivity()).get(PlayasViewModel::class.java)

        return inflater.inflate(R.layout.fragment_mapa, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}