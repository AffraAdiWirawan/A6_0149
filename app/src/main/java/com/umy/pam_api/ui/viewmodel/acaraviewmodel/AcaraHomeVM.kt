package com.umy.pam_api.ui.viewmodel.acaraviewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.umy.pam_api.model.Acara
import com.umy.pam_api.model.Lokasi
import com.umy.pam_api.repository.AcaraRepository
import com.umy.pam_api.repository.LokasiRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class AcaraUiState {
    data class Success(val acara: List<Acara>) : AcaraUiState()
    object Error : AcaraUiState()
    object Loading : AcaraUiState()
}

class AcaraViewModel(acr1: SavedStateHandle, private val acr: AcaraRepository): ViewModel(){
    var acrUiState : AcaraUiState by mutableStateOf(AcaraUiState.Loading)
        private set

    init {
        getAcr()
    }

    fun getAcr(){
        viewModelScope.launch {
            acrUiState = AcaraUiState.Loading
            acrUiState = try {
                AcaraUiState.Success(acr.getAcara())
            }catch (e:Exception) {
                AcaraUiState.Error
            }catch (e:Exception) {
                AcaraUiState.Error
            }
        }
    }

    fun deleteAcr(id_acara:String) {
        viewModelScope.launch {
            try {
                acr.deleteAcara(id_acara)
            }catch(e:IOException){
                AcaraUiState.Error
            }catch (e:HttpException) {
                AcaraUiState.Error
            }
        }
    }
}