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


class AcaraInsertVM(
    private val acaraRepository: AcaraRepository,
    private val lokasiRepository: LokasiRepository,
    private val klienRepository: KlienRepository,
) : ViewModel() {

    var lokasilist by mutableStateOf(listOf<Lokasi>())
        private set

    var klienlist by mutableStateOf(listOf<Klien>())
        private set

    var uiState by mutableStateOf(InsertAcaraUiState())
        private set

    fun updateInsertAcaraState(insertUiEvent: InsertAcaraUiEvent) {
        uiState = InsertAcaraUiState(insertUiEvent = insertUiEvent)
    }

    init {
        fetchDropdownData()
    }

    private fun fetchDropdownData() {
        viewModelScope.launch {
            try {
                lokasilist = lokasiRepository.getLokasi()
                Log.d("DropdownData", "Lokasi List: $lokasilist")
                klienlist = klienRepository.getKlien()
                Log.d("DropdownData", "Klien List: $klienlist")
            } catch (e: Exception) {
                Log.e("DropdownData", "Error fetching data: ${e.message}")
            }
        }
    }


    suspend fun insertAcara() {
        viewModelScope.launch {
            try {
                Log.d("Retrofit", "Data dikirim: ${uiState.insertUiEvent.toAcara()}")
                val response = acaraRepository.insertAcara(uiState.insertUiEvent.toAcara())
                Log.d("Retrofit", "Response: ${response}")
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("Retrofit", "Error: ${e.message}")
            }
        }
    }

}

fun InsertAcaraUiEvent.toAcara(): Acara = Acara(
    id_acara = id_acara,
    nama_acara = nama_acara,
    deskripsi_acara = deskripsi_acara,
    tanggal_mulai = tanggal_mulai,
    tanggal_berakhir = tanggal_berakhir,
    id_lokasi = id_lokasi,
    id_klien = id_klien,
    status_acara = status_acara
)

fun Acara.toUiStateAcara(): InsertAcaraUiState = InsertAcaraUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Acara.toInsertUiEvent(): InsertAcaraUiEvent = InsertAcaraUiEvent(
    id_acara = id_acara,
    nama_acara = nama_acara,
    deskripsi_acara = deskripsi_acara,
    tanggal_mulai = tanggal_mulai,
    tanggal_berakhir = tanggal_berakhir,
    id_lokasi = id_lokasi,
    id_klien = id_klien,
    status_acara = status_acara
)

data class InsertAcaraUiState(
    val insertUiEvent: InsertAcaraUiEvent = InsertAcaraUiEvent()
)

data class InsertAcaraUiEvent(
    val id_acara: String = "",
    val nama_acara: String = "",
    val deskripsi_acara: String = "",
    val tanggal_mulai: String = "",
    val tanggal_berakhir: String = "",
    val id_lokasi: String = "",
    val id_klien: String = "",
    val status_acara: String = ""
)