package com.umy.pam_api.model

import kotlinx.serialization.Serializable

@Serializable
data class Vendor (
    val id_vendor: String,
    val nama_vendor: String,
    val jenis_vendor: String,
    val kontak_vendor: String
)