package com.umy.pam_api.ui.viewmodel.acaraviewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umy.pam_api.model.Acara
import com.umy.pam_api.repository.AcaraRepository
import com.umy.pam_api.repository.KlienRepository
import com.umy.pam_api.repository.LokasiRepository
import com.umy.pam_api.ui.navigasi.DestinasiNavigasi
import kotlinx.coroutines.launch
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.umy.pam_api.model.Klien
import com.umy.pam_api.model.Lokasi
import com.umy.pam_api.ui.view.acara.DestinasiInsertAcara


class AcaraDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val acaraRepository: AcaraRepository
) : ViewModel() {
    private val id_acara: String = checkNotNull(savedStateHandle[DestinasiInsertAcara.ID_ACARA])

    var detailAcaraUiState: DetailAcaraUiState by mutableStateOf(DetailAcaraUiState())
        private set

    init {
        getAcaraById()
    }

    // Fetch acara detail by ID
    fun getAcaraById() {
        viewModelScope.launch {
            detailAcaraUiState = detailAcaraUiState.copy(isLoading = true) // Indicate loading state
            try {
                val result = acaraRepository.getAcaraById(id_acara)
                detailAcaraUiState = DetailAcaraUiState(
                    detailAcaraUiEvent = result.toDetailAcaraUiEvent(),
                    isLoading = false,
                    isError = false,
                    errorMessage = ""
                )
            } catch (e: Exception) {
                detailAcaraUiState = DetailAcaraUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Unknown error occurred"
                )
            }
        }
    }
}

// UI state for Acara detail
data class DetailAcaraUiState(
    val detailAcaraUiEvent: AcaraUiEvent = AcaraUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    // Check if the UI data is empty
    val isUiAcaraEmpty: Boolean
        get() = detailAcaraUiEvent == AcaraUiEvent()

    // Check if the UI data is not empty
    val isUiAcaraNotEmpty: Boolean
        get() = detailAcaraUiEvent != AcaraUiEvent()
}

// Event data class for Acara
data class AcaraUiEvent(
    val id_acara: String = "",
    val nama_acara: String = "",
    val deskripsi_acara: String = "",
    val tanggal_mulai: String = "",
    val tanggal_berakhir: String = "",
    val id_lokasi: String = "",
    val id_klien: String = "",
    val status_acara: String = ""
)

// Extension function to map Acara model to AcaraUiEvent
fun Acara.toDetailAcaraUiEvent(): AcaraUiEvent {
    return AcaraUiEvent(
        id_acara = id_acara,
        nama_acara = nama_acara,
        deskripsi_acara = deskripsi_acara,
        tanggal_mulai = tanggal_mulai,
        tanggal_berakhir = tanggal_berakhir,
        id_lokasi = id_lokasi,
        id_klien = id_klien,
        status_acara = status_acara
    )
}

