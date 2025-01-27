package com.umy.pam_api.ui.viewmodel.lokasiviewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umy.pam_api.model.Lokasi
import com.umy.pam_api.repository.LokasiRepository
import kotlinx.coroutines.launch

// ViewModel for inserting a location (Lokasi)
class LokasiInsertViewModel(
    lokasiRepository: SavedStateHandle,
    private val lks: LokasiRepository
) : ViewModel() {
    // Define the UI state variable for Insert operation
    var uiState by mutableStateOf(LokasiUiState())
        private set

    // Update the UI state with the new location event
    fun updateInsertLksState(lokasiUiEvent: LokasiUiEvent) {
        uiState = LokasiUiState(lokasiUiEvent = lokasiUiEvent)
    }

    // Insert the location by calling the repository
    fun insertLks() {
        viewModelScope.launch {
            try {
                // Call the insertLokasi method on the actual LokasiRepository
                lks.insertLokasi(uiState.lokasiUiEvent.toLksi())
            } catch (e: Exception) {
                // Handle the error (e.g., log or update the UI state with an error message)
                e.printStackTrace()
            }
        }
    }
}

// Define the state class for the UI state (Insert operation)
data class LokasiUiState(
    val lokasiUiEvent: LokasiUiEvent = LokasiUiEvent(),
    val error: String? = null
)

// Define the event class representing a location
data class LokasiUiEvent(
    val id_lokasi: String = "",
    val nama_lokasi: String = "",
    val alamat_lokasi: String = "",
    val kapasitas: String = ""
)

// Extension function to convert LokasiUiEvent to Lokasi (Model class)
fun LokasiUiEvent.toLksi(): Lokasi = Lokasi(
    id_lokasi = id_lokasi,
    nama_lokasi = nama_lokasi,
    alamat_lokasi = alamat_lokasi,
    kapasitas = kapasitas
)

// Extension function to convert Lokasi model to LokasiUiEvent
fun Lokasi.toUiStateLks(): LokasiUiEvent = LokasiUiEvent(
    id_lokasi = id_lokasi,
    nama_lokasi = nama_lokasi,
    alamat_lokasi = alamat_lokasi,
    kapasitas = kapasitas
)
