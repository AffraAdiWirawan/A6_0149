package com.umy.pam_api.ui.view.vendor

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
import com.umy.pam_api.model.Vendor
import com.umy.pam_api.ui.view.PenyediaViewModel
import com.umy.pam_api.ui.viewmodel.VendorHomeVM.VendorHomeVM
import com.umy.pam_api.ui.viewmodel.VendorHomeVM.VendorUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VendorScreen(
    navigateToItemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (String) -> Unit = {},
    navController: NavController,
    onBackClick: () -> Unit = {},
    viewModel: VendorHomeVM = viewModel(factory = PenyediaViewModel.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Daftar Vendor",
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = Color.Black,
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.getVdr() }) {
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
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Vendor")
            }
        },
    ) { innerPadding ->
        HomeStatus(
            VendorUiState = viewModel.vdrUiState,
            retryAction = { viewModel.getVdr() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteVdr(it.id_vendor)
                viewModel.getVdr()
            }
        )
    }
}

@Composable
fun HomeStatus(
    VendorUiState: VendorUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Vendor) -> Unit = {},
    onDetailClick: (String) -> Unit
) {
    when (VendorUiState) {
        is VendorUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is VendorUiState.Success -> {
            if (VendorUiState.vendor.isEmpty()) {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data vendor", style = MaterialTheme.typography.bodyMedium)
                }
            } else {
                MhsLayout(
                    vendor = VendorUiState.vendor,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = { onDetailClick(it.id_vendor) },
                    onDeleteClick = { onDeleteClick(it) }
                )
            }
        }
        is VendorUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
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
    vendor: List<Vendor>,
    modifier: Modifier = Modifier,
    onDetailClick: (Vendor) -> Unit,
    onDeleteClick: (Vendor) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(vendor) { vendor ->
            LksCard(
                vendor = vendor,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(vendor) },
                onDeleteClick = { onDeleteClick(vendor) }
            )
        }
    }
}

@Composable
fun LksCard(
    vendor: Vendor,
    modifier: Modifier = Modifier,
    onDeleteClick: (Vendor) -> Unit = {},
    onEditClick: (Vendor) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 12.dp,
            pressedElevation = 16.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFADD8E6)
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
                    text = vendor.jenis_vendor,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))

                IconButton(onClick = { onDeleteClick(vendor) }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Vendor",
                        tint = MaterialTheme.colorScheme.error
                    )
                }

                IconButton(onClick = { onEditClick(vendor) }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Vendor",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Divider()

            Column {
                Text(
                    text = "ID Vendor: ${vendor.id_vendor}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Nama Vendor: ${vendor.nama_vendor}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Kontak Vendor: ${vendor.kontak_vendor}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}
