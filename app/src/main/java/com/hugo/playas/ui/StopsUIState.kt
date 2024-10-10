package com.example.networkapp.state

import Playa


sealed class StopsUIState (val state: AppStatus) {
    object Loading : StopsUIState(AppStatus.LOADING)
    //Igual hay que poner un array de playas o solo una playa
    data class Success(val playa: List<Playa>): StopsUIState(AppStatus.SUCCESS) //Ojo puede ser una lista
    //data class Success(val playa: Playa): StopsUIState(AppStatus.SUCCESS) //Ojo puede ser una lista
    data class Error (val message:String): StopsUIState(AppStatus.ERROR)
}