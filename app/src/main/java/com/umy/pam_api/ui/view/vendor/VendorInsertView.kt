package com.umy.pam_api.ui.view.vendor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.umy.pam_api.ui.navigasi.CostumeTopAppBar
import com.umy.pam_api.ui.navigasi.DestinasiNavigasi
import com.umy.pam_api.ui.view.PenyediaViewModel
import com.umy.pam_api.ui.viewmodel.VendorHomeVM.VendorInsertVM
import com.umy.pam_api.ui.viewmodel.VendorHomeVM.VendorUiEvent
import com.umy.pam_api.ui.viewmodel.VendorHomeVM.VendorUiState1
import kotlinx.coroutines.launch

object DestinasiEntryVendor: DestinasiNavigasi {
    override val route = "item_vendor"
    override val titleRes = "Entry Vendor"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryVdrScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: VendorInsertVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryVendor.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBody(
            vendorUiState = viewModel.uiState,
            onSiswaValueChange = viewModel::updateInsertVdrState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertVdr()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBody(
    vendorUiState: VendorUiState1,
    onSiswaValueChange: (VendorUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ) {
        FormInput(
            vendorUiEvent = vendorUiState.vendorUiEvent,
            onValueChange = onSiswaValueChange,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            Text(text = "Simpan",style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInput(
    vendorUiEvent: VendorUiEvent,
    modifier: Modifier = Modifier,
    onValueChange: (VendorUiEvent) -> Unit,
    enabled: Boolean = true
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)  // Slightly reduced space to bring elements closer together
    ) {
        OutlinedTextField(
            value = vendorUiEvent.id_vendor,
            onValueChange = { onValueChange(vendorUiEvent.copy(id_vendor = it)) },
            label = { Text("ID Vendor") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            shape = MaterialTheme.shapes.medium, // Rounded corners for the text field
            colors = MaterialTheme.colorScheme.run {
                TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = primary,  // Focused border color
                    unfocusedBorderColor = onSurfaceVariant.copy(alpha = 0.5f)  // Subtle unfocused border
                )
            }
        )

        // Nama Lokasi TextField
        OutlinedTextField(
            value = vendorUiEvent.nama_vendor,
            onValueChange = { onValueChange(vendorUiEvent.copy(nama_vendor = it)) },
            label = { Text("Nama Vendor") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            shape = MaterialTheme.shapes.medium,
            colors = MaterialTheme.colorScheme.run {
                TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = primary,
                    unfocusedBorderColor = onSurfaceVariant.copy(alpha = 0.5f)
                )
            }
        )

        // Alamat Lokasi TextField
        OutlinedTextField(
            value = vendorUiEvent.jenis_vendor,
            onValueChange = { onValueChange(vendorUiEvent.copy(jenis_vendor = it)) },
            label = { Text("Jenis Vendor") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            shape = MaterialTheme.shapes.medium,
            colors = MaterialTheme.colorScheme.run {
                TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = primary,
                    unfocusedBorderColor = onSurfaceVariant.copy(alpha = 0.5f)
                )
            }
        )

        // Kapasitas TextField
        OutlinedTextField(
            value = vendorUiEvent.kontak_vendor,
            onValueChange = { onValueChange(vendorUiEvent.copy(kontak_vendor = it)) },
            label = { Text("Kontak Vendor") },
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            singleLine = true,
            shape = MaterialTheme.shapes.medium,
            colors = MaterialTheme.colorScheme.run {
                TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = primary,
                    unfocusedBorderColor = onSurfaceVariant.copy(alpha = 0.5f)
                )
            }
        )

        // Instructions Text
        if (enabled) {
            Text(
                text = "Isi Semua Data!",
                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.secondary),  // Slightly smaller text with secondary color
                modifier = Modifier.padding(12.dp)
            )
        }

        // Divider with softer color
        Divider(
            thickness = 1.dp,  // Thinner divider for subtle separation
            modifier = Modifier.padding(12.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)  // Lighter divider color
        )
    }

}