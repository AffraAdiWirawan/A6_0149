package com.umy.pam_api.ui.view.acara

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.umy.pam_api.ui.navigasi.CostumeTopAppBar
import com.umy.pam_api.ui.navigasi.DestinasiNavigasi
import com.umy.pam_api.ui.view.PenyediaViewModel
import com.umy.pam_api.ui.viewmodel.acaraviewmodel.AcaraUpdateVM
import kotlinx.coroutines.launch
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.umy.pam_api.model.Klien
import com.umy.pam_api.model.Lokasi
import com.umy.pam_api.ui.viewmodel.acaraviewmodel.InsertAcaraUiEvent
import com.umy.pam_api.ui.viewmodel.acaraviewmodel.InsertAcaraUiState

object DestinasiUpdateAcara : DestinasiNavigasi {
    override val route = "update acara"
    const val ID_ACARA = "id_acara"
    override val titleRes = "Detail Acara"
    val routeWithArg = "$route/{$ID_ACARA}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AcaraUpdateView(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AcaraUpdateVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val acaraUiState = viewModel.uiState
    val klienList = viewModel.klienlist // Ambil daftar klien dari ViewModel
    val lokasiList = viewModel.lokasilist // Ambil daftar lokasi dari ViewModel
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Daftar Acara",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = Color.Black
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        EntryAcaraBody1(
            uiState = acaraUiState,
            onAcaraValueChange = { viewModel.updateInsertAcaraState(it) },
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updateAcara()
                    navigateBack()
                }
            },
            klienList = klienList, // Pass klienList to body
            lokasiList = lokasiList, // Pass lokasiList to body
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()) // Enables scrolling when content overflows
        )
    }
}

@Composable
fun EntryAcaraBody1(
    uiState: InsertAcaraUiState,
    onAcaraValueChange: (InsertAcaraUiEvent) -> Unit,
    onSaveClick: () -> Unit,
    klienList: List<Klien>, // List of Klien
    lokasiList: List<Lokasi>, // List of Lokasi
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(16.dp)) {
        // Nama Acara
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            Text("Nama Acara")
            OutlinedTextField(
                value = uiState.insertUiEvent.nama_acara,
                onValueChange = {
                    onAcaraValueChange(uiState.insertUiEvent.copy(nama_acara = it))
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Nama Acara") }
            )
        }

        // Deskripsi Acara
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            Text("Deskripsi Acara")
            OutlinedTextField(
                value = uiState.insertUiEvent.deskripsi_acara,
                onValueChange = {
                    onAcaraValueChange(uiState.insertUiEvent.copy(deskripsi_acara = it))
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Deskripsi Acara") }
            )
        }

        // Tanggal Mulai
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            Text("Tanggal Mulai")
            OutlinedTextField(
                value = uiState.insertUiEvent.tanggal_mulai,
                onValueChange = {
                    onAcaraValueChange(uiState.insertUiEvent.copy(tanggal_mulai = it))
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Tanggal Mulai") }
            )
        }

        // Tanggal Berakhir
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            Text("Tanggal Berakhir")
            OutlinedTextField(
                value = uiState.insertUiEvent.tanggal_berakhir,
                onValueChange = {
                    onAcaraValueChange(uiState.insertUiEvent.copy(tanggal_berakhir = it))
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Tanggal Berakhir") }
            )
        }

        // Lokasi Dropdown
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            Text("Pilih Lokasi")
            DropdownSelected(
                selectedValue = lokasiList.find { it.id_lokasi == uiState.insertUiEvent.id_lokasi }?.nama_lokasi ?: "Pilih Lokasi",
                options = lokasiList.map { it.nama_lokasi },
                onValueChangedEvent = { lokasi ->
                    val selectedLokasi = lokasiList.find { it.nama_lokasi == lokasi }
                    selectedLokasi?.let {
                        onAcaraValueChange(uiState.insertUiEvent.copy(id_lokasi = it.id_lokasi))
                    }
                }
            )
        }

        // Klien Dropdown
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            Text("Pilih Klien")
            DropdownSelected(
                selectedValue = klienList.find { it.id_klien == uiState.insertUiEvent.id_klien }?.nama_klien ?: "Pilih Klien",
                options = klienList.map { it.nama_klien },
                onValueChangedEvent = { klien ->
                    val selectedKlien = klienList.find { it.nama_klien == klien }
                    selectedKlien?.let {
                        onAcaraValueChange(uiState.insertUiEvent.copy(id_klien = it.id_klien))
                    }
                }
            )
        }

        // Status Acara
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            Text("Status Acara")
            OutlinedTextField(
                value = uiState.insertUiEvent.status_acara,
                onValueChange = {
                    onAcaraValueChange(uiState.insertUiEvent.copy(status_acara = it))
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Status Acara") }
            )
        }

        // Button Simpan
        Button(onClick = onSaveClick, modifier = Modifier.padding(top = 16.dp)) {
            Text("Simpan")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelected(
    selectedValue: String,
    options: List<String>,
    onValueChangedEvent: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
    ) {
        OutlinedTextField(
            readOnly = true,
            value = selectedValue,
            onValueChange = {},
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier.menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        expanded = false
                        onValueChangedEvent(option)
                    }
                )
            }
        }
    }
}

