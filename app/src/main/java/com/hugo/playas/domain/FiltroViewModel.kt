package com.hugo.playas.domain

import androidx.lifecycle.ViewModel

class FiltroViewModel: ViewModel(){

    var zonaSeleccionada: Int=-1
    var distancia: Double=-1.0
    var bandera: Boolean=false
    var activo: Boolean=false
    var busqueda: String=""
    var esTablet: Boolean=false
}