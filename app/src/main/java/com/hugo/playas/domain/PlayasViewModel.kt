package com.hugo.playas.domain

import Playa
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.networkapp.data.ApiResult
import com.example.networkapp.data.Repository
import com.example.networkapp.state.StopsUIState
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PlayasViewModel: ViewModel() {

    private val _stopsUIStateObservable = MutableLiveData<StopsUIState>()
    val stopsUIStateObservable: LiveData<StopsUIState> get() = _stopsUIStateObservable

    var playaSeleccionada: Playa? = null


    init {
        getPlayasList()
    }

    fun getPlayasList()=
        viewModelScope.launch {
            Repository.updatePlayasData().map { result ->
                when (result) {
                    is ApiResult.Success -> StopsUIState.Success(result.data!!)
                    is ApiResult.Error -> StopsUIState.Error(result.message.toString())
                    ApiResult.Loading -> StopsUIState.Loading
                }
            }.collect {
                _stopsUIStateObservable.value = it
            }
        }
}