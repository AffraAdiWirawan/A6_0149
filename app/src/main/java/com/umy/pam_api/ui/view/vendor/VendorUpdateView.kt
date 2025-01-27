package com.umy.pam_api.ui.view.vendor

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.umy.pam_api.ui.navigasi.CostumeTopAppBar
import com.umy.pam_api.ui.navigasi.DestinasiNavigasi
import com.umy.pam_api.ui.view.PenyediaViewModel
import com.umy.pam_api.ui.viewmodel.VendorHomeVM.VendorUpdateVM
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiUpdateVendor: DestinasiNavigasi {
    override val route = "update vendor"
    const val ID_VENDOR = "id_vendor"
    override val titleRes = "Detail Vendor"
    val routeWithArg = "$route/{$ID_VENDOR}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VendorUpdateView(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigate:()-> Unit,
    viewModel: VendorUpdateVM = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateVendor.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ){padding ->
        EntryBody(
            modifier = Modifier.padding(padding),
            vendorUiState = viewModel.updateUiState,
            onSiswaValueChange = viewModel::updateInsertMhsState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateVdr()
                    delay(600)
                    withContext(Dispatchers.Main) {
                        onNavigate()
                    }
                }
            }
        )
    }
}