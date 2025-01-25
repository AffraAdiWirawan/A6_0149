package com.umy.pam_api.model

import kotlinx.serialization.Serializable

@Serializable
data class Lokasi(
    val id_lokasi: String,
    val nama_lokasi: String,
    val alamat_lokasi: String,
    val kapasitas: String
)