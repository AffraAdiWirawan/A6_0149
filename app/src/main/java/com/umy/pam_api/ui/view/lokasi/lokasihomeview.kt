package com.umy.pam_api.ui.view.lokasi

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.umy.pam_api.R
import com.umy.pam_api.model.Lokasi
import com.umy.pam_api.ui.navigasi.DestinasiNavigasi
import com.umy.pam_api.ui.view.PenyediaViewModel
import com.umy.pam_api.ui.viewmodel.lokasiviewmodel.LokasUiState
import com.umy.pam_api.ui.viewmodel.lokasiviewmodel.LokasiViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LokasiScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    navController: NavController, // Pass the navController here
    onBackClick: () -> Unit = {},
    viewModel: LokasiViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Daftar Lokasi",  // Set title text
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = Color.Black,  // Set blue color
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.getLks() }) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToItemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(16.dp) // Add padding to the FAB
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Lokasi")
            }
        },
    ) { innerPadding ->
        HomeStatus(
            LokasUiState = viewModel.lksUiState,
            retryAction = { viewModel.getLks() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteLks(it.id_lokasi)
                viewModel.getLks()
            }
        )
    }
}

@Composable
fun HomeStatus(
    LokasUiState: LokasUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Lokasi) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (LokasUiState) {
        is LokasUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is LokasUiState.Success -> {
            if (LokasUiState.lokasi.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data lokasi", style = MaterialTheme.typography.bodyMedium)
                }
            } else {
                MhsLayout(
                    lokasi = LokasUiState.lokasi,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_lokasi) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is LokasUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(R.drawable.no_wifi),
            contentDescription = stringResource(R.string.loading)
        )
    }
}

@Composable
fun OnError(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Image(painter = painterResource(id = R.drawable.no_wifi), contentDescription = "")
            Text(
                text = stringResource(R.string.loading_failed),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(16.dp)
            )
            Button(onClick = retryAction, modifier = Modifier.padding(top = 16.dp)) {
                Text(stringResource(R.string.retry))
            }
        }
    }
}

@Composable
fun MhsLayout(
    lokasi: List<Lokasi>,
    modifier: Modifier = Modifier,
    onDetailClick: (Lokasi) -> Unit,
    onDeleteClick: (Lokasi) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(lokasi) { lokasi ->
            LksCard(
                lokasi = lokasi,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(lokasi) },
                onDeleteClick = { onDeleteClick(lokasi) }
            )
        }
    }
}

@Composable
fun LksCard(
    lokasi: Lokasi,
    modifier: Modifier = Modifier,
    onDeleteClick: (Lokasi) -> Unit = {},
    onEditClick: (Lokasi) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,  // Increased roundness for a more modern feel
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp, // Increased elevation for more shadow depth
            pressedElevation = 16.dp // Higher elevation on press
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFADD8E6) // Slightly transparent background for soft look
        ),
    )  {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = lokasi.nama_lokasi,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))

                // Delete Button
                IconButton(onClick = { onDeleteClick(lokasi) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Lokasi",
                        tint = MaterialTheme.colorScheme.error
                    )
                }

                // Edit Button
                IconButton(onClick = { onEditClick(lokasi) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Lokasi",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Divider()

            Column {
                Text(
                    text = "ID Lokasi: ${lokasi.id_lokasi}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Nama Lokasi: ${lokasi.nama_lokasi}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Alamat Lokasi: ${lokasi.alamat_lokasi}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
