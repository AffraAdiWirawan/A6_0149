package com.umy.pam_api.ui.navigasi

interface DestinasiNavigasi {
    val route: String
    val titleRes: String
}
object DestinasiMain: DestinasiNavigasi{
    override val route  = "homeapp"
    override val titleRes = "Home"
}

object DestinasiLokasi: DestinasiNavigasi {
    override val route ="lokasi"
    override val titleRes = "Home Lokasi"
}
object DestinasiAcara: DestinasiNavigasi {
    override val route ="acara"
    override val titleRes = "Home Acara"
}

object DestinasiDetaillokasi: DestinasiNavigasi {
    override val route = "detail lokasi"
    const val ID_LOKASI = "id_lokasi"
    override val titleRes = "Detail Lokasi"
    val routeWithArg1 = "$route/{$ID_LOKASI}"
}

object DestinasiKlien: DestinasiNavigasi {
    override val route ="Klien"
    override val titleRes = "Home Klien"
}

object DestinasiVendor: DestinasiNavigasi {
    override val route ="Vendor"
    override val titleRes = "Home Vendor"
}

object DestinasiDetailVendor: DestinasiNavigasi {
    override val route = "detail vendor"
    const val ID_VENDOR = "id_vendor"
    override val titleRes = "Detail Vendor"
    val routeWithArg = "$route/{$ID_VENDOR}"
}

object DestinasiUpdateLokasi: DestinasiNavigasi {
    override val route = "update lokasi"
    const val ID_LOKASI = "id_lokasi"
    override val titleRes = "Detail Lokasi"
    val routeWithArg = "$route/{$ID_LOKASI}"
}

object DestinasiUpdateVendor: DestinasiNavigasi {
    override val route = "update vendor"
    const val ID_VENDOR = "id_vendor"
    override val titleRes = "Detail Vendor"
    val routeWithArg = "$route/{$ID_VENDOR}"
}

object DestinasiEntryKlien: DestinasiNavigasi {
    override val route = "entry klien"
    override val titleRes = "Entry Klien"
}

object DestinasiDetailKlien: DestinasiNavigasi {
    override val route = "detail klien"
    const val ID_KLIEN = "id_klien"
    override val titleRes = "Detail Klien"
    val routeWithArg = "$route/{$ID_KLIEN}"
}

