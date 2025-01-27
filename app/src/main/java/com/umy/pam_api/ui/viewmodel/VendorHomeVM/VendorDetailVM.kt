package com.umy.pam_api.ui.viewmodel.VendorHomeVM

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umy.pam_api.model.Vendor
import com.umy.pam_api.repository.VendorRepository
import com.umy.pam_api.ui.navigasi.DestinasiNavigasi
import kotlinx.coroutines.launch

object DestinasiDetailVendor: DestinasiNavigasi {
    override val route = "detail vendor"
    const val ID_VENDOR = "id_vendor"
    override val titleRes = "Detail Vendor"
    val routeWithArg = "$route/{$ID_VENDOR}"
}

class VendorDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val vendorRepository: VendorRepository
) : ViewModel() {
    val id_vendor: String = checkNotNull(savedStateHandle[DestinasiDetailVendor.ID_VENDOR])

    var detailVendorUiState: DetailVendorUiState by mutableStateOf(DetailVendorUiState())
        private set

    init {
        getVendorById()
    }

    fun getVendorById() {
        viewModelScope.launch {
            detailVendorUiState = DetailVendorUiState(isLoading = true)
            try {
                val result = vendorRepository.getVendorById(id_vendor)
                detailVendorUiState = DetailVendorUiState(
                    detailVendorUiEvent = result.toDetailVendorUiEvent(),
                    isLoading = false
                )
            } catch (e: Exception) {
                detailVendorUiState = DetailVendorUiState(
                    isLoading = false,
                    isError = true,
                    errorMessage = e.message ?: "Unknown error occurred"
                )
            }
        }
    }
}


data class DetailVendorUiState(
    val detailVendorUiEvent: VendorUiEvent = VendorUiEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
){
    val isUiVendorEmpty: Boolean
        get() = detailVendorUiEvent == VendorUiEvent()

    val isUiVendorNotEmpty: Boolean
        get() = detailVendorUiEvent != VendorUiEvent()
}

fun Vendor.toDetailVendorUiEvent(): VendorUiEvent {
    return VendorUiEvent(
        id_vendor = id_vendor,
        nama_vendor = nama_vendor,
        jenis_vendor = jenis_vendor,
        kontak_vendor = kontak_vendor
    )
}