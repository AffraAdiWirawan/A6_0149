package com.umy.pam_api.ui.view

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.umy.pam_api.application.MahasiswaApplications
import com.umy.pam_api.ui.viewmodel.VendorHomeVM.VendorDetailViewModel
import com.umy.pam_api.ui.viewmodel.VendorHomeVM.VendorHomeVM
import com.umy.pam_api.ui.viewmodel.VendorHomeVM.VendorInsertVM
import com.umy.pam_api.ui.viewmodel.VendorHomeVM.VendorUpdateVM
import com.umy.pam_api.ui.viewmodel.acaraviewmodel.AcaraDetailViewModel
import com.umy.pam_api.ui.viewmodel.acaraviewmodel.AcaraInsertVM
import com.umy.pam_api.ui.viewmodel.acaraviewmodel.AcaraViewModel
import com.umy.pam_api.ui.viewmodel.klienviewmodel.KlienDetailVM
import com.umy.pam_api.ui.viewmodel.klienviewmodel.KlienHomeVM
import com.umy.pam_api.ui.viewmodel.klienviewmodel.KlienInsertVM
import com.umy.pam_api.ui.viewmodel.klienviewmodel.KlienUpdateVM
import com.umy.pam_api.ui.viewmodel.lokasiviewmodel.LokasiDetailViewModel
import com.umy.pam_api.ui.viewmodel.lokasiviewmodel.LokasiInsertViewModel
import com.umy.pam_api.ui.viewmodel.lokasiviewmodel.LokasiViewModel
import com.umy.pam_api.ui.viewmodel.lokasiviewmodel.UpdateLokasiViewModel

object PenyediaViewModel{
    val Factory = viewModelFactory {
        initializer {
            LokasiViewModel(
                createSavedStateHandle(),
                MahasiswaApplication().container.lokasiRepository)
        }
        initializer {
            AcaraViewModel(
                createSavedStateHandle(),
                MahasiswaApplication().container.acaraRepository)
        }
        initializer {
            AcaraDetailViewModel(
                createSavedStateHandle(),
                MahasiswaApplication().container.acaraRepository)
        }
        initializer {
            AcaraInsertVM(
                MahasiswaApplication().container.acaraRepository, MahasiswaApplication().container.lokasiRepository, MahasiswaApplication().container.klienRepository)
        }
        initializer {
            LokasiViewModel(
                createSavedStateHandle(),
                MahasiswaApplication().container.lokasiRepository)
        }
        initializer {
            LokasiDetailViewModel(
                createSavedStateHandle(),
                MahasiswaApplication().container.lokasiRepository)
        }
        initializer {
            LokasiInsertViewModel(
                createSavedStateHandle(),
                MahasiswaApplication().container.lokasiRepository)
        }

        initializer {
            UpdateLokasiViewModel(
                createSavedStateHandle(),
                MahasiswaApplication().container.lokasiRepository)
        }

        initializer {
            KlienHomeVM(
                createSavedStateHandle(),
                MahasiswaApplication().container.klienRepository)
        }
        initializer {
            KlienInsertVM(
                createSavedStateHandle(),
                MahasiswaApplication().container.klienRepository)
        }
        initializer {
            KlienDetailVM(
                createSavedStateHandle(),
                MahasiswaApplication().container.klienRepository)
        }
        initializer {
            KlienUpdateVM(
                createSavedStateHandle(),
                MahasiswaApplication().container.klienRepository)
        }
        initializer {
            VendorHomeVM(
                createSavedStateHandle(),
                MahasiswaApplication().container.vendorRepository)
        }
        initializer {
            VendorInsertVM(
                createSavedStateHandle(),
                MahasiswaApplication().container.vendorRepository)
        }
        initializer {
            VendorDetailViewModel(
                createSavedStateHandle(),
                MahasiswaApplication().container.vendorRepository)
        }
        initializer {
            VendorUpdateVM(
                createSavedStateHandle() ,
                MahasiswaApplication().container.vendorRepository)
        }
    }
}

fun CreationExtras.MahasiswaApplication(): MahasiswaApplications =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MahasiswaApplications)