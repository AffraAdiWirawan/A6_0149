package com.umy.pam_api.ui.viewmodel.VendorHomeVM

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.umy.pam_api.model.Vendor
import com.umy.pam_api.repository.VendorRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed class VendorUiState {
    data class Success(val vendor: List<Vendor>) : VendorUiState()
    object Error : VendorUiState()
    object Loading : VendorUiState()
}

class VendorHomeVM(vdr1: SavedStateHandle, private val vdr: VendorRepository) : ViewModel() {
    var vdrUiState: VendorUiState by mutableStateOf(VendorUiState.Loading)
        private set

    init {
        getVdr() // Initial load of vendors when the ViewModel is created
    }

    fun getVdr() {
        viewModelScope.launch {
            vdrUiState = VendorUiState.Loading // Set the state to loading before the network request
            try {
                val vendors = vdr.getVendor() // Fetch vendor data from the repository
                vdrUiState = VendorUiState.Success(vendors) // Update state to success with the fetched data
            } catch (e: IOException) {
                vdrUiState = VendorUiState.Error // Handle network issues
            } catch (e: HttpException) {
                vdrUiState = VendorUiState.Error // Handle HTTP issues like 404 or 500
            } catch (e: Exception) {
                vdrUiState = VendorUiState.Error // Handle any other unexpected errors
            }
        }
    }


    fun deleteVdr(id_vendor: String) {
        viewModelScope.launch {
            try {
                vdr.deleteVendor(id_vendor) // Call the repository to delete the vendor
                getVdr() // Refresh the list of vendors after deletion
            } catch (e: IOException) {
                vdrUiState = VendorUiState.Error // Handle network issues during deletion
            } catch (e: HttpException) {
                vdrUiState = VendorUiState.Error // Handle HTTP issues during deletion
            } catch (e: Exception) {
                vdrUiState = VendorUiState.Error // Handle any other unexpected errors during deletion
            }
        }
    }
}
