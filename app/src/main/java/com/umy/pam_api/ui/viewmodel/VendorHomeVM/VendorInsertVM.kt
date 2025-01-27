package com.umy.pam_api.ui.viewmodel.VendorHomeVM

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umy.pam_api.model.Vendor
import com.umy.pam_api.repository.VendorRepository
import kotlinx.coroutines.launch

class VendorInsertVM(
    vendorRepository: SavedStateHandle,
    private val vdr: VendorRepository
) : ViewModel() {
    // Define the UI state variable for Insert operation
    var uiState by mutableStateOf(VendorUiState1())
        private set

    // Update the UI state wih the new location event
    fun updateInsertVdrState(vendorUiEvent: VendorUiEvent) {
        uiState = VendorUiState1(vendorUiEvent = vendorUiEvent)
    }

    // Insert the location by calling the repository
    fun insertVdr() {
        viewModelScope.launch {
            try {
                // Call the insertLokasi method on the actual LokasiRepository
                vdr.insertVendor(uiState.vendorUiEvent.toVdr())
            } catch (e: Exception) {
                // Handle the error (e.g., log or update the UI state with an error message)
                e.printStackTrace()
            }
        }
    }
}

// Define the state class for the UI state (Insert operation)
data class VendorUiState1(
    val vendorUiEvent: VendorUiEvent = VendorUiEvent(),
    val error: String? = null
)

// Define the event class representing a location
data class VendorUiEvent(
    val id_vendor: String = "",
    val nama_vendor: String = "",
    val jenis_vendor: String = "",
    val kontak_vendor: String = ""
)

fun VendorUiEvent.toVdr(): Vendor = Vendor(
    id_vendor = id_vendor,
    nama_vendor = nama_vendor,
    jenis_vendor = jenis_vendor,
    kontak_vendor = kontak_vendor
)

fun Vendor.toUiStateLks(): VendorUiEvent = VendorUiEvent(
    id_vendor = id_vendor,
    nama_vendor = nama_vendor,
    jenis_vendor = jenis_vendor,
    kontak_vendor = kontak_vendor
)
