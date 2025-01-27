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

object DestinasiUpdateVendor: DestinasiNavigasi {
    override val route = "update vendor"
    const val ID_VENDOR = "id_vendor"
    override val titleRes = "Detail Vendor"
    val routeWithArg = "$route/{$ID_VENDOR}"
}


class VendorUpdateVM (
    savedStateHandle: SavedStateHandle,
    private val vdr: VendorRepository
): ViewModel(){
    var updateUiState by mutableStateOf(VendorUiState1())
        private set

    private val _id_vendor : String = checkNotNull(savedStateHandle[DestinasiUpdateVendor.ID_VENDOR])

    init {
        viewModelScope.launch {
            updateUiState = vdr.getVendorById(_id_vendor)
                .toUIStateVdr()
        }
    }

    fun updateInsertMhsState(vendorUiEvent:VendorUiEvent){
        updateUiState = VendorUiState1(vendorUiEvent = vendorUiEvent)
    }

    suspend fun updateVdr(){
        viewModelScope.launch {
            try {
                vdr.updateVendor(_id_vendor, updateUiState.vendorUiEvent.toVdr())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}


fun Vendor.toUIStateVdr(): VendorUiState1 = VendorUiState1(
    vendorUiEvent = this.toDetailVendorUiEvent(),
)