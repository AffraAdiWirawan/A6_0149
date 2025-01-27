package com.umy.pam_api.ui.view.lokasi

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
import com.umy.pam_api.ui.viewmodel.lokasiviewmodel.UpdateLokasiViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateLokasi: DestinasiNavigasi {
    override val route = "update lokasi"
    const val ID_LOKASI = "id_lokasi"
    override val titleRes = "Detail Lokasi"
    val routeWithArg = "$route/{$ID_LOKASI}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateLokasiView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateLokasiViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val lokasiUiState = viewModel.updatelokasiUIState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryLokasi.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBody(
            lokasiUiState = lokasiUiState,
            onSiswaValueChange = { viewModel.updateInsertLksState(it) },
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateLokasi()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}