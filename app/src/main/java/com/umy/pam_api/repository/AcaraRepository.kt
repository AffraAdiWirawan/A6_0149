package com.umy.pam_api.repository

import AcaraService
import KlienService
import com.umy.pam_api.model.Acara

interface AcaraRepository {
    suspend fun getAcara(): List<Acara>
    suspend fun insertAcara(acara: Acara)
    suspend fun updateAcara(id_acara: String, acara: Acara)
    suspend fun deleteAcara(id_acara: String)
    suspend fun getAcaraById(id_acara: String): Acara
}

class NetworkAcaraRepository(private val acaraService: AcaraService)
    : AcaraRepository
{

    override suspend fun getAcara(): List<Acara> = acaraService.getAcara()

    override suspend fun insertAcara(acara: Acara) {
        acaraService.insertAcara(acara)
        println(acara)
    }

    override suspend fun updateAcara(id_acara: String, acara: Acara) {
        acaraService.updateAcara(id_acara,acara)
    }

    override suspend fun deleteAcara(id_acara: String) {
        try {
            val response = acaraService.deleteAcara(id_acara)
            if (!response.isSuccessful) {
                throw Exception("Failed to delete acara. HTTP Status Code: ${response.code()}")
            }
            else{
                response.message()
                println(response.message())
            }
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getAcaraById(id_acara: String): Acara {
        return acaraService.getAcaraById(id_acara)
    }
}