package com.umy.pam_api.service_api

import com.umy.pam_api.model.Lokasi
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface LokasiService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("bacalokasi.php")
    suspend fun getLokasi(): List<Lokasi>

    @GET("baca1lokasi.php/{id_lokasi}")
    suspend fun getLokasiById(@Query("id_lokasi") id_lokasi: String): Lokasi

    @POST("insertlokasi.php")
    suspend fun insertLokasi(@Body lokasi: Lokasi)

    @PUT("editlokasi.php")
    suspend fun updateLokasi(@Query("id_lokasi")id_lokasi: String, @Body lokasi: Lokasi)

    @DELETE("deletelokasi.php/{id_lokasi}")
    suspend fun deleteLokasi(@Query("id_lokasi")id_lokasi: String): Response<Void>
}