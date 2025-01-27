 package com.umy.pam_api.ui.view.klien

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
import com.umy.pam_api.ui.view.lokasi.DestinasiEntryLokasi
import com.umy.pam_api.ui.viewmodel.klienviewmodel.KlienUpdateVM
import com.umy.pam_api.ui.viewmodel.lokasiviewmodel.UpdateLokasiViewModel
import kotlinx.coroutines.launch

object DestinasiUpdateKlien: DestinasiNavigasi {
    override val route = "update klien"
    const val ID_KLIEN = "id_klien"
    override val titleRes = "Detail Klien"
    val routeWithArg = "$route/{$ID_KLIEN}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KlienUpdateView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: KlienUpdateVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val klienUiState1 = viewModel.updateUiState
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiUpdateKlien.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
            EntryBody(
            klienUiState = klienUiState1,
            onSiswaValueChange = { viewModel.updateInsertKlnState(it) },
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateKln()
                    navigateBack()
                }
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}