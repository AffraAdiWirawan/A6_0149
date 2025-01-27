package com.umy.pam_api.ui.view.lokasi

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
import com.umy.pam_api.model.Lokasi
import com.umy.pam_api.ui.navigasi.DestinasiNavigasi
import com.umy.pam_api.ui.view.PenyediaViewModel
import com.umy.pam_api.ui.viewmodel.lokasiviewmodel.DetailLokasiUiState
import com.umy.pam_api.ui.viewmodel.lokasiviewmodel.LokasiDetailViewModel
import com.umy.pam_api.ui.viewmodel.lokasiviewmodel.toLksi

object DestinasiDetailLokasi: DestinasiNavigasi {
    override val route = "detail lokasi"
    const val ID_LOKASI = "id_lokasi"
    override val titleRes = "Detail Lokasi"
    val routeWithArg = "$route/{$ID_LOKASI}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LokasiDetailScreen(
    navigateBack: () -> Unit,
    navigateToEdit: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: LokasiDetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Detail Lokasi",  // Set title text
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
                    IconButton(onClick = { viewModel.getLokasiById() }) {
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
        BodyDetailLks(
            detailLokasiUiState = viewModel.detailLokasiUiState,
            modifier = Modifier.padding(innerPadding)
        )
    }
}


@Composable
fun BodyDetailLks(
    detailLokasiUiState: DetailLokasiUiState,
    modifier: Modifier = Modifier
) {
    when {
        detailLokasiUiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        detailLokasiUiState.isError -> {
            Box(
                modifier = modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = detailLokasiUiState.errorMessage,
                    color = Color.Black, // Text color black for error
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        detailLokasiUiState.isUiLokasiNotEmpty -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                ItemDetailLks(
                    lokasi = detailLokasiUiState.detailLokasiUiEvent.toLksi(),
                    modifier = modifier
                )
            }
        }
    }
}

@Composable
fun ItemDetailLks(
    modifier: Modifier = Modifier,
    lokasi: Lokasi
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
            ComponentDetailLks(judul = "ID Lokasi", isinya = lokasi.id_lokasi)
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentDetailLks(judul = "Nama", isinya = lokasi.nama_lokasi)
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentDetailLks(judul = "Alamat", isinya = lokasi.alamat_lokasi)
            Spacer(modifier = Modifier.padding(8.dp))
            ComponentDetailLks(judul = "Kapasitas", isinya = lokasi.kapasitas)
            Spacer(modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun ComponentDetailLks(
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

