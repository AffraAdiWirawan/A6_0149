package com.umy.pam_api.ui.viewmodel.klienviewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umy.pam_api.model.Klien
import com.umy.pam_api.repository.KlienRepository
import kotlinx.coroutines.launch
import java.io.IOException

// Define UI states
sealed class KlienUiState {
    data class Success(val klien: List<Klien>) : KlienUiState()
    object Error : KlienUiState()
    object Loading : KlienUiState()
}

class KlienHomeVM(
    klienRepository1: SavedStateHandle,
    private val klienRepository: KlienRepository // Injecting KlienRepository correctly){}
) : ViewModel() {

    // UI State to manage the list of Klien
    var KUiState: KlienUiState by mutableStateOf(KlienUiState.Loading)
        private set

    init {
        getKln() // Get Klien data when ViewModel is initialized
    }

    // Function to fetch Klien data
    fun getKln() {
        viewModelScope.launch {
            KUiState = KlienUiState.Loading // Start by showing loading state
            try {
                val klienList = klienRepository.getKlien() // Fetch data from the repository
                KUiState = KlienUiState.Success(klienList) // Update state on success
            } catch (e: IOException) {
                KUiState = KlienUiState.Error // Handle network issues (e.g., no internet)
            } catch (e: Exception) {
                KUiState = KlienUiState.Error // Handle unexpected errors (generic)
            }
        }
    }

    // Function to delete a Klien by ID
    fun deleteKln(id_klien: String) {
        viewModelScope.launch {
            try {
                klienRepository .deleteKlien(id_klien) // Call repository to delete the client
                getKln() // Refresh list after deletion
            } catch (e: IOException) {
                KUiState = KlienUiState.Error // Handle network issues during deletion
            } catch (e: Exception) {
                KUiState = KlienUiState.Error // Handle any other unexpected errors during deletion
            }
        }
    }
}
