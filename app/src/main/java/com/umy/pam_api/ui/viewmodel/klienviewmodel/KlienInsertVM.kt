package com.umy.pam_api.ui.viewmodel.klienviewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umy.pam_api.model.Klien
import com.umy.pam_api.model.Vendor
import com.umy.pam_api.repository.KlienRepository
import kotlinx.coroutines.launch

class KlienInsertVM(
    klienRepository: SavedStateHandle,
    private val kln: KlienRepository
) : ViewModel() {
    // Define the UI state variable for Insert operation
    var uiState by mutableStateOf(KlienUiState1())
        private set

    // Update the UI state wih the new location event
    fun updateInsertKlnState(klienUiEvent: KlienUiEvent) {
        uiState = KlienUiState1(klienUiEvent = klienUiEvent)
    }

    // Insert the location by calling the repository
    fun insertKlien() {
        viewModelScope.launch {
            try {
                // Call the insertLokasi method on the actual LokasiRepository
                kln.insertKlien(uiState.klienUiEvent.toKln())
            } catch (e: Exception) {
                // Handle the error (e.g., log or update the UI state with an error message)
                e.printStackTrace()
            }
        }
    }
}

// Define the state class for the UI state (Insert operation)
data class KlienUiState1(
    val klienUiEvent: KlienUiEvent = KlienUiEvent(),
    val error: String? = null
)

// Define the event class representing a location
data class KlienUiEvent(
    val id_klien: String = "",
    val nama_klien: String = "",
    val kontak_klien: String = "",
)

fun KlienUiEvent.toKln(): Klien = Klien(
    id_klien = id_klien,
    nama_klien = nama_klien,
    kontak_klien = kontak_klien
)

fun Klien.toUiStateKln(): KlienUiEvent =  KlienUiEvent(
    id_klien = id_klien,
    nama_klien =  nama_klien,
    kontak_klien = kontak_klien
)
