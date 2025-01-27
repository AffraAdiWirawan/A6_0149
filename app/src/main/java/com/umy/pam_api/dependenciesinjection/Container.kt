package com.umy.pam_api.dependenciesinjection

import AcaraService
import KlienService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.umy.pam_api.model.Klien
import com.umy.pam_api.repository.AcaraRepository
import com.umy.pam_api.repository.KlienRepository
import com.umy.pam_api.repository.LokasiRepository
import com.umy.pam_api.repository.NetworkAcaraRepository
import com.umy.pam_api.repository.NetworkKlienRepository
import com.umy.pam_api.repository.NetworkLokasiRepository
import com.umy.pam_api.repository.NetworkVendorRepository
import com.umy.pam_api.repository.VendorRepository
import com.umy.pam_api.service_api.LokasiService
import com.umy.pam_api.service_api.VendorService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val vendorRepository : VendorRepository
    val lokasiRepository: LokasiRepository
    val klienRepository: KlienRepository
    val acaraRepository: AcaraRepository
}

class TugasContainer : AppContainer {
    private val baseUrl = "http://10.0.2.2:8090/umyTI/" //http://10.0.2.2:8080/umyTI/ untuk lokal
    private val json = Json { ignoreUnknownKeys = true}
    private val retrofit: Retrofit = Retrofit.Builder()

        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val acaraService: AcaraService by lazy {
        retrofit.create(AcaraService::class.java)
    }
    override val acaraRepository: AcaraRepository by lazy {
        NetworkAcaraRepository (acaraService)
    }
    private val lokasiService: LokasiService by lazy {
        retrofit.create(LokasiService::class.java)
    }

    override val lokasiRepository: LokasiRepository by lazy {
        NetworkLokasiRepository (lokasiService)
    }
    private val klienService: KlienService by lazy {
        retrofit.create(KlienService::class.java)
    }
    override val klienRepository: KlienRepository by lazy {
        NetworkKlienRepository (klienService)
    }

    private val vendorService: VendorService by lazy {
        retrofit.create(VendorService::class.java)
    }
    override val vendorRepository: VendorRepository by lazy {
        NetworkVendorRepository (vendorService)
    }
}