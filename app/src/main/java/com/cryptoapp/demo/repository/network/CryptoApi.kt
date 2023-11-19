package com.cryptoapp.demo.repository.network

import com.cryptoapp.demo.helper.CONSTANTS
import com.cryptoapp.demo.model.cryptoInfo.Response
import com.cryptoapp.demo.model.logo.CryptoLogo
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface CryptoApi {

    @GET("listings/latest")
    suspend fun getCrypto(
        @Query("limit") limit: Int,
        @Header("X-CMC_PRO_API_KEY") key: String,
    ): Response

    @GET("info")
    suspend fun getLogo(
        @Query("id") id: Int,
        @Header("X-CMC_PRO_API_KEY") key: String
    ): CryptoLogo

}