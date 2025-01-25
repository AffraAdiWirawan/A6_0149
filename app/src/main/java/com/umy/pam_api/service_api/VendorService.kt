package com.umy.pam_api.service_api

import com.umy.pam_api.model.Vendor
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface VendorService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )
    @GET("bacavendor.php")
    suspend fun getVendor(): List<Vendor>

    @GET("baca1vendor.php/{id_vendor}")
    suspend fun getVendorById(@Query("id_vendor") id_vendor: String): Vendor

    @POST("insertvendor.php")
    suspend fun insertVendor(@Body vendor: Vendor)

    @PUT("editvendor.php")
    suspend fun updateVendor(@Query("id_vendor")id_vendor: String, @Body vendor: Vendor)

    @DELETE("deletevendor.php/{id_vendor}")
    suspend fun deleteVendor(@Query("id_vendor")id_vendor: String): Response<Void>
}