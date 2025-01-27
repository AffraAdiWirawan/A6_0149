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
import com.umy.pam_api.repository.VendorRepository
import com.umy.pam_api.ui.navigasi.DestinasiNavigasi
import com.umy.pam_api.ui.viewmodel.VendorHomeVM.VendorUiEvent
import com.umy.pam_api.ui.viewmodel.VendorHomeVM.VendorUiState1
import com.umy.pam_api.ui.viewmodel.VendorHomeVM.toDetailVendorUiEvent
import com.umy.pam_api.ui.viewmodel.VendorHomeVM.toVdr
import kotlinx.coroutines.launch

object DestinasiUpdateKlien: DestinasiNavigasi {
    override val route = "update klien"
    const val ID_KLIEN = "id_klien"
    override val titleRes = "Detail Klien"
    val routeWithArg = "$route/{$ID_KLIEN}"
}

class KlienUpdateVM (
    savedStateHandle: SavedStateHandle,
    private val kln: KlienRepository
): ViewModel(){
    var updateUiState by mutableStateOf(KlienUiState1())
        private set

    private val _id_klien : String = checkNotNull(savedStateHandle[DestinasiUpdateKlien.ID_KLIEN])

    init {
        viewModelScope.launch {
            updateUiState = kln.getKlienById(_id_klien)
                .toUIStateKln()
        }
    }

    fun updateInsertKlnState(klienUiEvent: KlienUiEvent){
        updateUiState = KlienUiState1(klienUiEvent = klienUiEvent)
    }

    suspend fun updateKln(){
        viewModelScope.launch {
            try {
                kln.updateKlien(_id_klien, updateUiState.klienUiEvent.toKln())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}


fun Klien.toUIStateKln(): KlienUiState1 = KlienUiState1(
    klienUiEvent = this.toDetailKlienUiEvent(),
)