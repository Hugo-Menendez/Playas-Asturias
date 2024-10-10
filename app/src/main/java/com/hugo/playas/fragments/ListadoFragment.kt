package com.hugo.playas.fragments

import Playa
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.networkapp.state.StopsUIState
import com.hugo.playas.R
import com.hugo.playas.databinding.FragmentListadoBinding
import com.hugo.playas.domain.FiltroViewModel
import com.hugo.playas.domain.PlayasViewModel
import com.hugo.playas.ui.PlayaListAdapter


class ListadoFragment : Fragment(), FragmentFiltro.OnActionListener {

    //Para la vinculación de vistas
    private lateinit var _binding: FragmentListadoBinding
    private val binding get() = _binding!!

    //Declarar el ViewModel y adaptador
    private lateinit var playasViewModel: PlayasViewModel
    private lateinit var filtrosViewModel: FiltroViewModel
    private lateinit var playaListAdapter: PlayaListAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Indicar que tiene su propio menú
        setHasOptionsMenu(true)

        //Instanciamos el mismo VM que la actividad
        playasViewModel = ViewModelProvider(requireActivity()).get(PlayasViewModel::class.java)
        filtrosViewModel = ViewModelProvider(requireActivity()).get(FiltroViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentListadoBinding.inflate(inflater, container, false)

        // Inicializar el RecyclerView
        val recyclerView= _binding.rvListado
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Crear el adaptador
        playaListAdapter = PlayaListAdapter { playa -> itemSeleccionado(playa) }

        // Asignar el adaptador al RecyclerView
        recyclerView.adapter = playaListAdapter

        // Observar los cambios en el estado del UI
        //Log.d("DEBUG", "Antes en el When")
        playasViewModel.stopsUIStateObservable.observe(viewLifecycleOwner) { result ->
            when (result) {
                is StopsUIState.Success -> {
                    Log.d("DEBUG", "Exito al traer los datos")
                    //Se pasan las playas al adapter
                    playaListAdapter.submitList(result.playa)
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

        //Listener para identificar cuando se hace scroll en el RV y de esta manera quitar el foco del edittext y ocultar el teclado
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy != 0) {
                    binding.editText.clearFocus() // Desselecciona el EditText
                    val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(requireView().windowToken, 0)
                }
            }
        })

        // Configurar un listener para escuchar cambios en el EditText
        binding.editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            // Después de cambiar el texto
            override fun afterTextChanged(s: Editable?) {
                filtrosViewModel.busqueda=s.toString()
                buscar()
            }
        })

        return binding.root
    }

    //Función que mostrará el fragmento con la información detallada de la playa
    fun itemSeleccionado(playa:Playa){
        playasViewModel.playaSeleccionada=playa

        if(!filtrosViewModel.esTablet){
            findNavController().navigate(R.id.action_listadoFragment_to_detallesFragment)
        } else {
            val fragment = DetallesFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.contenedor_derecho, fragment)
                .commit()
        }

    }

    //Inflamos el menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_action, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_map ->  {
                findNavController().navigate(R.id.action_listadoFragment_to_mapa)
            }
            R.id.action_filtro ->  {
                if(!filtrosViewModel.activo) mostrarFragmentoFiltro()
                else ocultarFragmentoFiltro()
            }
        }
        return true
    }

    //Funcion que se encarga de obtener las playas que coinciden con la cadena de texto introducida
    override fun buscar(){
        playasViewModel.stopsUIStateObservable.observe(viewLifecycleOwner) { result ->
            when (result) {
                is StopsUIState.Success -> {
                    //Lista sobre la que vamos aplicar los filtros
                    var lista= result.playa

                    //Filtros
                    //Filtrar con bandera azul
                    if(filtrosViewModel.bandera){
                        lista = lista.filter { it.BanderaAzul.contains( "true", ignoreCase = true) }
                    }

                   //Filtro por zona
                    if(filtrosViewModel.zonaSeleccionada!=-1){
                        if( filtrosViewModel.zonaSeleccionada==1) {
                            lista =
                                lista.filter { it.Zona.contains("Oriente", ignoreCase = true) }
                        }

                        if( filtrosViewModel.zonaSeleccionada==2) {
                            lista =
                                lista.filter { it.Zona.contains("Centro", ignoreCase = true) }
                        }
                        if( filtrosViewModel.zonaSeleccionada==3) {
                            lista =
                                lista.filter { it.Zona.contains("Occidente", ignoreCase = true) }
                        }
                    }


                    val busqueda = lista.filter { it.Nombre.contains( filtrosViewModel.busqueda, ignoreCase = true) || it.Concejo.contains( filtrosViewModel.busqueda, ignoreCase = true) || it.Zona.contains( filtrosViewModel.busqueda, ignoreCase = true) }
                    //Se pasan las playas al adapter
                    playaListAdapter.submitList(busqueda)
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

    //Mostrar el fragmento que contiene los filtros
    fun mostrarFragmentoFiltro(){
        //Para la festion del fragmento local  Filtro
        val fragmentManager = getChildFragmentManager()
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragmentoNuevo = FragmentFiltro()
        fragmentoNuevo.onActionListener = this

        // Reemplazar el contenido del ContainerView con el fragmento creado
        fragmentTransaction.replace(R.id.contenedorFiltro, fragmentoNuevo)
        fragmentTransaction.commit()
        filtrosViewModel.activo=true
    }

    //Ocultar el fragmento que contiene los filtros
    fun ocultarFragmentoFiltro(){

        val fragmentManager = getChildFragmentManager()
        val fragmento = fragmentManager.findFragmentById(R.id.contenedorFiltro)

        if (fragmento != null) {
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.hide(fragmento)
            fragmentTransaction.commit()
            filtrosViewModel.activo=false
        }
    }

}