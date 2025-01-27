package com.umy.pam_api.ui.view.acara

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.umy.pam_api.model.Acara
import com.umy.pam_api.model.Klien
import com.umy.pam_api.ui.navigasi.DestinasiNavigasi
import com.umy.pam_api.ui.view.PenyediaViewModel
import com.umy.pam_api.ui.viewmodel.acaraviewmodel.AcaraDetailViewModel
import com.umy.pam_api.ui.viewmodel.acaraviewmodel.AcaraUiEvent
import com.umy.pam_api.ui.viewmodel.acaraviewmodel.DetailAcaraUiState


object DestinasiDetailAcara : DestinasiNavigasi {
    override val route = "detail acara"
    const val ID_ACARA = "id_acara"
    override val titleRes = "Detail Acara"
    val routeWithArg = "$route/{$ID_ACARA}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AcaraDetailScreen(
    navigateBack: () -> Unit,
    navigateToEdit: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AcaraDetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Detail Acara",  // Set title text
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = Color.Black,  // Set blue color
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = navigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.getAcaraById() }) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                },
                scrollBehavior = scrollBehavior,
                // Add elevation to give it a shadow effect
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToEdit,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Lokasi"
                )
            }
        }
    ) { innerPadding ->
        BodyDetailAcr(
            detailAcaraUiState = viewModel.detailAcaraUiState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}


@Composable
fun BodyDetailAcr(
    detailAcaraUiState: DetailAcaraUiState,
    modifier: Modifier = Modifier
) {
    when {
        detailAcaraUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        detailAcaraUiState.isError -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = detailAcaraUiState.errorMessage,
                    color = Color.Black, // Text color black for error
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        detailAcaraUiState.isUiAcaraNotEmpty -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailAcr(
                    acara = detailAcaraUiState.detailAcaraUiEvent.toAcr(),
                    modifier = modifier
                )
            }
        }
    }
}

fun AcaraUiEvent.toAcr(): Acara = Acara(
    id_acara = id_acara,
    nama_acara = nama_acara,
    deskripsi_acara = deskripsi_acara,
    tanggal_mulai = tanggal_mulai,
    tanggal_berakhir = tanggal_berakhir,
    id_lokasi = id_lokasi,
    id_klien = id_klien,
    status_acara = status_acara
)

@Composable
fun ItemDetailAcr(
    modifier: Modifier = Modifier,
    acara: Acara,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1E8A68), // Dark Blue background for card
            contentColor = Color.White // White text color for contrast
        ),
        shape = MaterialTheme.shapes.medium, // Rounded corners
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            ComponentDetailAcr(judul = "ID Acara", isinya = acara.id_acara)
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentDetailAcr(judul = "Nama Acara", isinya = acara.nama_acara)
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentDetailAcr(judul = "Deskripsi Acara", isinya = acara.deskripsi_acara)
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentDetailAcr(judul = "Tanggal Mulai", isinya = acara.tanggal_mulai)
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentDetailAcr(judul = "Tanggal Berakhir", isinya = acara.tanggal_berakhir)
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentDetailAcr(judul = "ID Lokasi", isinya = acara.id_lokasi)
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentDetailAcr(judul = "ID Klien", isinya = acara.id_klien)
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentDetailAcr(judul = "Status Acara", isinya = acara.status_acara)
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun ComponentDetailAcr(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        // Title with blue color
        Text(
            text = "$judul : ",
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White // Dark blue for titles
        )
        Spacer(modifier = Modifier.height(4.dp))

        // Content with black color for better readability
        Text(
            text = isinya,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black // Black color for content text
        )
    }
}

