package com.umy.pam_api.ui.navigasi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.umy.pam_api.HomeApp
import com.umy.pam_api.ui.view.acara.AcaraDetailScreen
import com.umy.pam_api.ui.view.acara.AcaraScreen
import com.umy.pam_api.ui.view.acara.AcaraUpdateView
import com.umy.pam_api.ui.view.acara.DestinasiDetailAcara
import com.umy.pam_api.ui.view.acara.DestinasiInsertAcara
import com.umy.pam_api.ui.view.acara.DestinasiUpdateAcara
import com.umy.pam_api.ui.view.acara.EntryAcaraScreen
import com.umy.pam_api.ui.view.klien.DestinasiEntryKlien
import com.umy.pam_api.ui.view.klien.DestinasiUpdateKlien
import com.umy.pam_api.ui.view.klien.EntryKlnScreen
import com.umy.pam_api.ui.view.klien.KlienDetailScreen
import com.umy.pam_api.ui.view.klien.KlienScreen
import com.umy.pam_api.ui.view.klien.KlienUpdateView
import com.umy.pam_api.ui.view.lokasi.DestinasiDetailLokasi
import com.umy.pam_api.ui.view.lokasi.DestinasiEntryLokasi
import com.umy.pam_api.ui.view.lokasi.EntryLksScreen
import com.umy.pam_api.ui.view.lokasi.LokasiDetailScreen
import com.umy.pam_api.ui.view.lokasi.LokasiScreen
import com.umy.pam_api.ui.view.lokasi.UpdateLokasiView
import com.umy.pam_api.ui.view.vendor.DestinasiEntryVendor
import com.umy.pam_api.ui.view.vendor.EntryVdrScreen
import com.umy.pam_api.ui.view.vendor.VendorDetailScreen
import com.umy.pam_api.ui.view.vendor.VendorScreen
import com.umy.pam_api.ui.view.vendor.VendorUpdateView

@Composable
fun PengelolaHalaman(
    modifier : Modifier = Modifier,
    navController: NavHostController = rememberNavController()){
    NavHost(
        navController = navController,
        startDestination = DestinasiMain.route,
        modifier = Modifier,
    ) {
        composable(route = DestinasiMain.route) {
            HomeApp(
                onHalamanHomeDosen = {
                    navController.navigate(DestinasiAcara.route)
                },
                onHalamanHomeMK = {
                    navController.navigate(DestinasiKlien.route)
                },
                onHalamanHomeLKS = {
                    navController.navigate(DestinasiLokasi.route)
                },
                onHalamanHomeVDR = {
                    navController.navigate(DestinasiVendor.route)
                },
                modifier = modifier
            )
        }
        composable(route = DestinasiAcara.route) {
            AcaraScreen(
                navController = navController,
                navigateToItemEntry = { navController.navigate(DestinasiInsertAcara.route) },
                onDetailClick = { id_acara ->
                    navController.navigate("${DestinasiDetailAcara.route}/$id_acara")
                }
            )
        }
        composable(route = DestinasiInsertAcara.route) {
            EntryAcaraScreen(
                navigateBack = {
                    navController.navigate(DestinasiAcara.route) {
                        popUpTo(DestinasiAcara.route) {
                            inclusive = true
                        }
                    }
                },
                navController = navController
            )
        }
        composable(
            DestinasiDetailAcara.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailAcara.ID_ACARA) {
                    type = NavType.StringType
                }
            )
        ) {
            val id_acara = it.arguments?.getString(DestinasiDetailAcara.ID_ACARA)
            id_acara?.let {
                AcaraDetailScreen(
                    navigateBack = {
                        navController.navigate(DestinasiAcara.route) {
                            popUpTo(DestinasiAcara.route) {
                                inclusive = true
                            }
                        }
                    },
                    navigateToEdit = {
                        navController.navigate("${DestinasiUpdateAcara.route}/$it")
                    }
                )
            }
        }
        composable(
            DestinasiUpdateAcara.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateAcara.ID_ACARA) {
                    type = NavType.StringType
                }
            )
        ) {
            val id_acara = it.arguments?.getString(DestinasiUpdateAcara.ID_ACARA)
            id_acara?.let {
                AcaraUpdateView(
                    navigateBack = {
                        navController.navigate("${DestinasiDetailAcara.route}/$it") {
                            popUpTo("${DestinasiDetailAcara.route}/$it") {
                                inclusive = true
                            }
                        }
                    },
                )
            }
        }


        composable(route = DestinasiVendor.route) {
            VendorScreen(
                navController = navController,
                navigateToItemEntry = { navController.navigate(DestinasiEntryVendor.route) },
                onDetailClick = { id_vendor ->
                    navController.navigate("${DestinasiDetailVendor.route}/$id_vendor")
                }
            )
        }

        composable(route = DestinasiEntryVendor.route) {
            EntryVdrScreen(navigateBack = {
                navController.navigate(DestinasiVendor.route) {
                    popUpTo(DestinasiVendor.route) {
                        inclusive = true
                    }
                }
            })
        }
        composable(
            DestinasiDetailVendor.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailVendor.ID_VENDOR) {
                    type = NavType.StringType
                }
            )
        ) {
            val id_vendor = it.arguments?.getString(DestinasiDetailVendor.ID_VENDOR)
            id_vendor?.let {
                VendorDetailScreen(
                    navigateBack = {
                        navController.navigate(DestinasiVendor.route) {
                            popUpTo(DestinasiVendor.route) {
                                inclusive = true
                            }
                        }
                    },
                    navigateToEdit = {
                        navController.navigate("${DestinasiUpdateVendor.route}/$it")
                    }
                )
            }
        }
        composable(
            DestinasiUpdateVendor.routeWithArg,
            arguments = listOf(navArgument(DestinasiDetailVendor.ID_VENDOR) {
                type = NavType.StringType
            }
            )
        ) {
            val id_vendor = it.arguments?.getString(DestinasiUpdateVendor.ID_VENDOR)
            id_vendor?.let { id_vendor ->
                VendorUpdateView(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }
        composable(route = DestinasiKlien.route) {
            KlienScreen(
                navController = navController,
                navigateToItemEntry = { navController.navigate(DestinasiEntryKlien.route) },
                onDetailClick = { id_klien ->
                    navController.navigate("${DestinasiDetailKlien.route}/$id_klien")
                }
            )
        }
        composable(
            DestinasiDetailKlien.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailKlien.ID_KLIEN) {
                    type = NavType.StringType
                }
            )
        ) {
            val id_klien = it.arguments?.getString(DestinasiDetailKlien.ID_KLIEN)
            id_klien?.let {
                KlienDetailScreen(
                    navigateBack = {
                        navController.navigate(DestinasiKlien.route) {
                            popUpTo(DestinasiKlien.route) {
                                inclusive = true
                            }
                        }
                    },
                    navigateToEdit = {
                        navController.navigate("${DestinasiUpdateKlien.route}/$it")
                    }
                )
            }
        }
        composable(
            DestinasiUpdateKlien.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateKlien.ID_KLIEN) {
                    type = NavType.StringType
                }
            )
        ) {
            val id_klien = it.arguments?.getString(DestinasiUpdateKlien.ID_KLIEN)
            id_klien?.let {
                KlienUpdateView(
                    navigateBack = {
                        navController.navigate("${DestinasiDetailKlien.route}/$it") {
                            popUpTo("${DestinasiDetailKlien.route}/$it") {
                                inclusive = true
                            }
                        }
                    },

                    )
            }
        }
        composable(route = DestinasiEntryKlien.route) {
            EntryKlnScreen(navigateBack = {
                navController.navigate(DestinasiKlien.route) {
                    popUpTo(DestinasiKlien.route) {
                        inclusive = true
                    }
                }
            })
        }
        composable(route = DestinasiLokasi.route) {
            LokasiScreen(
                navController = navController,
                navigateToItemEntry = { navController.navigate(DestinasiEntryLokasi.route) },
                onDetailClick = { id_lokasi ->
                    navController.navigate("${DestinasiDetailLokasi.route}/$id_lokasi")
                }
            )
        }
        composable(route = DestinasiEntryLokasi.route) {
            EntryLksScreen(navigateBack = {
                navController.navigate(DestinasiLokasi.route) {
                    popUpTo(DestinasiLokasi.route) {
                        inclusive = true
                    }
                }
            }
            )
        }

        composable(
            DestinasiDetailLokasi.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailLokasi.ID_LOKASI) {
                    type = NavType.StringType
                }
            )
        ) {
            val id_lokasi = it.arguments?.getString(DestinasiDetailLokasi.ID_LOKASI)
            id_lokasi?.let {
                LokasiDetailScreen(
                    navigateBack = {
                        navController.navigate(DestinasiLokasi.route) {
                            popUpTo(DestinasiLokasi.route) {
                                inclusive = true
                            }
                        }
                    },
                    navigateToEdit = {
                        navController.navigate("${DestinasiUpdateLokasi.route}/$it")
                    }
                )
            }
        }

        composable(
            DestinasiUpdateLokasi.routeWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateLokasi.ID_LOKASI) {
                    type = NavType.StringType
                }
            )
        ) {
            val id_lokasi = it.arguments?.getString(DestinasiUpdateLokasi.ID_LOKASI)
            id_lokasi?.let {
                UpdateLokasiView(
                    navigateBack = {
                        navController.navigate("${DestinasiDetailLokasi.route}/$it") {
                            popUpTo("${DestinasiDetailLokasi.route}/$it") {
                                inclusive = true
                            }
                        }
                    },
                )
            }
        }
    }

}