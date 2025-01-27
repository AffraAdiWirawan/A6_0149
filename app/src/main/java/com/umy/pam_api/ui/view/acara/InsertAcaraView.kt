package com.umy.pam_api.ui.view.acara

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.umy.pam_api.model.Lokasi
import com.umy.pam_api.ui.navigasi.DestinasiNavigasi
import com.umy.pam_api.ui.view.PenyediaViewModel
import com.umy.pam_api.ui.viewmodel.acaraviewmodel.AcaraInsertVM
import com.umy.pam_api.ui.viewmodel.acaraviewmodel.InsertAcaraUiEvent
import com.umy.pam_api.ui.viewmodel.acaraviewmodel.InsertAcaraUiState
import kotlinx.coroutines.launch

object DestinasiInsertAcara : DestinasiNavigasi {
    override val route = "insert acara"
    const val ID_ACARA = "id_acara"
    override val titleRes = "Insert Acara"
    val routeWithArg = "${DestinasiDetailAcara.route}/{$ID_ACARA}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryAcaraScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: AcaraInsertVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val uiState = viewModel.uiState

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Daftar Sesi",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = Color.Black
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        EntryAcaraBody(
            viewModel = viewModel,
            onSaveClick = { updateEvent -> viewModel.updateInsertAcaraState(updateEvent) },
            uiState = uiState,
            onClick = {
                coroutineScope.launch {
                    viewModel.insertAcara()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        )
    }
}

@Composable
fun EntryAcaraBody(
    viewModel: AcaraInsertVM,
    onSaveClick: (InsertAcaraUiEvent) -> Unit,
    onClick: () -> Unit,
    uiState: InsertAcaraUiState,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        InsertAcaraScreen(
            viewModel = viewModel,
            insertAcaraUiEvent = uiState.insertUiEvent,
            onValueChange = onSaveClick
        )

        Button(
            onClick = onClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Simpan")
        }
    }
}

@Composable
fun InsertAcaraScreen(
    insertAcaraUiEvent: InsertAcaraUiEvent,
    onValueChange: (InsertAcaraUiEvent) -> Unit,
    viewModel: AcaraInsertVM,
    modifier: Modifier = Modifier
) {
    val lokasiList = viewModel.lokasilist
    val klienList = viewModel.klienlist
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        OutlinedTextField(
            value = insertAcaraUiEvent.id_acara,
            onValueChange = { newIdAcara -> onValueChange(insertAcaraUiEvent.copy(id_acara = newIdAcara)) },
            label = { Text("ID Acara") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = insertAcaraUiEvent.nama_acara,
            onValueChange = { namaAcara -> onValueChange(insertAcaraUiEvent.copy(nama_acara = namaAcara)) },
            label = { Text("Nama Acara") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = insertAcaraUiEvent.deskripsi_acara,
            onValueChange = { deskripsiAcara -> onValueChange(insertAcaraUiEvent.copy(deskripsi_acara = deskripsiAcara)) },
            label = { Text("Deskripsi Acara") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3
        )

        OutlinedTextField(
            value = insertAcaraUiEvent.tanggal_mulai,
            onValueChange = { tanggalMulai -> onValueChange(insertAcaraUiEvent.copy(tanggal_mulai = tanggalMulai)) },
            label = { Text("Tanggal Mulai") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3
        )

        OutlinedTextField(
            value = insertAcaraUiEvent.tanggal_berakhir,
            onValueChange = { tanggalBerakhir -> onValueChange(insertAcaraUiEvent.copy(tanggal_berakhir = tanggalBerakhir)) },
            label = { Text("Tanggal Berakhir") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3
        )

        // Dropdown for Pilih Lokasi
        DropdownSelected(
            selectedValue = lokasiList.find { it.id_lokasi == insertAcaraUiEvent.id_lokasi }?.nama_lokasi ?: "Pilih Lokasi",
            options = lokasiList.map { it.nama_lokasi },
            label = "Pilih Lokasi",
            onValueChangedEvent = { lokasi ->
                val selectedLokasi = lokasiList.find { it.nama_lokasi == lokasi }
                selectedLokasi?.let {
                    onValueChange(insertAcaraUiEvent.copy(id_lokasi = it.id_lokasi))
                }
            }
        )

        // Dropdown for Pilih Klien
        DropdownSelected(
            selectedValue = klienList.find { it.id_klien == insertAcaraUiEvent.id_klien }?.nama_klien ?: "Pilih Klien",
            options = klienList.map { it.nama_klien },
            label = "Pilih Klien",
            onValueChangedEvent = { klien ->
                val selectedKlien = klienList.find { it.nama_klien == klien }
                selectedKlien?.let {
                    onValueChange(insertAcaraUiEvent.copy(id_klien = it.id_klien))
                }
            }
        )

        OutlinedTextField(
            value = insertAcaraUiEvent.status_acara,
            onValueChange = { statusAcara -> onValueChange(insertAcaraUiEvent.copy(status_acara = statusAcara)) },
            label = { Text("Status Acara") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownSelected(
    selectedValue: String,
    options: List<String>,
    label: String,
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
            label = { Text(text = label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = OutlinedTextFieldDefaults.colors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(text = option) },
                    onClick = {
                        expanded = false
                        onValueChangedEvent(option)
                    }
                )
            }
        }
    }
}
