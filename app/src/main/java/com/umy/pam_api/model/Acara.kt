package com.umy.pam_api.model

import kotlinx.serialization.Serializable


@Serializable
data class Acara(
    val id_acara: String,
    val nama_acara: String,
    val deskripsi_acara: String,
    val tanggal_mulai: String,
    val tanggal_berakhir: String,
    val id_lokasi: String,
    val id_klien: String,
    val status_acara: String
)
