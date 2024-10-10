package com.hugo.playas

import android.content.Context
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.networkapp.state.StopsUIState
import com.google.android.material.snackbar.Snackbar
import com.hugo.playas.databinding.ActivityMainBinding
import com.hugo.playas.domain.FiltroViewModel
import com.hugo.playas.domain.PlayasViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //Declarar los ViewModel
    private lateinit var playasViewModel: PlayasViewModel
    private lateinit var filtrosViewModel: FiltroViewModel

    private lateinit var navController: NavController



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Crear la instancia del ViewModel
        playasViewModel = ViewModelProvider(this).get(PlayasViewModel::class.java)
        filtrosViewModel = ViewModelProvider(this).get(FiltroViewModel::class.java)

        filtrosViewModel.esTablet = isTablet(this)

        // Realizar la adaptación de la interfaz según el tipo de dispositivo
        if (filtrosViewModel.esTablet) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            setContentView(R.layout.activity_main_tablet)
        } else {
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
        }

        // Configurar el componente de Navigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.contenedor) as NavHostFragment
        navController = navHostFragment.navController




    }

    //Funcion para comprobar si es una tablet o un movil
    fun isTablet(context: Context): Boolean {
        val configuration = resources.configuration
        return  configuration.smallestScreenWidthDp >= 600
    }
}