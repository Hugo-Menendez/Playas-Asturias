package com.example.networkapp.data

import android.util.Log
import com.example.networkapp.network.RestApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

object Repository {
    fun updatePlayasData() =
        // Se crea un flujo
        flow {
            // Se realiza la petición al servicio
            try {
                // Respuesta correcta
                val listaPlayas = RestApi.retrofitService.getPlayas()
                // Se emite el estado Succes y se incluyen los datos recibidos
                emit(ApiResult.Success(listaPlayas))
            } catch (e: Exception) {

                // Error en la red
                // Se emite el estado de Error con el mensaje que lo explica
                Log.d("DEBUG ERROR", e.toString())
                emit(ApiResult.Error(e.toString()))
            }
            // El flujo se ejecuta en el hilo I/O
        }.flowOn(Dispatchers.IO)
}