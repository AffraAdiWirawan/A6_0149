package com.umy.pam_api.ui.viewmodel.lokasiviewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umy.pam_api.model.Lokasi
import com.umy.pam_api.repository.LokasiRepository
import com.umy.pam_api.ui.navigasi.DestinasiNavigasi
import kotlinx.coroutines.launch

object DestinasiDetailLokasi: DestinasiNavigasi {
    override val route = "detail lokasi"
    const val ID_LOKASI = "id_lokasi"
    override val titleRes = "Detail Lokasi"
    val routeWithArg = "$route/{$ID_LOKASI}"
}

class LokasiDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val lokasiRepository: LokasiRepository
) : ViewModel() {
    val id_lokasi: String = checkNotNull(savedStateHandle[DestinasiDetailLokasi.ID_LOKASI])

    var detailLokasiUiState: DetailLokasiUiState by mutableStateOf(DetailLokasiUiState())
        private set

    init {
        getLokasiById()
    }

    fun getLokasiById() {
        viewModelScope.launch {
            detailLokasiUiState = DetailLokasiUiState(isLoading = true)
            try {
                val result = lokasiRepository.getLokasiById(id_lokasi)
                detailLokasiUiState = DetailLokasiUiState(
                    detailLokasiUiEvent = result.toDetailLokasiUiEvent(),
                    isLoading = false
                )
            } catch (e: Exception) {
                detailLokasiUiState = DetailLokasiUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Unknown error occurred"
                )
            }
        }
    }
}


data class DetailLokasiUiState(
    val detailLokasiUiEvent: LokasiUiEvent = LokasiUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
){
    val isUiLokasiEmpty: Boolean
        get() = detailLokasiUiEvent == LokasiUiEvent()

    val isUiLokasiNotEmpty: Boolean
        get() = detailLokasiUiEvent != LokasiUiEvent()
}

fun Lokasi.toDetailLokasiUiEvent(): LokasiUiEvent {
    return LokasiUiEvent(
        id_lokasi = id_lokasi,
        nama_lokasi = nama_lokasi,
        alamat_lokasi = alamat_lokasi,
        kapasitas = kapasitas
    )
}