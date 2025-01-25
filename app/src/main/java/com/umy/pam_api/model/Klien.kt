package com.umy.pam_api.model

import kotlinx.serialization.Serializable
@Serializable
data class Klien (
    val id_klien: String,
    val nama_klien: String,
    val kontak_klien: String,
)
