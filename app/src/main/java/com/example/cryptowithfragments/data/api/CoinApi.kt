package com.example.cryptowithfragments.data.api

import com.example.cryptowithfragments.domain.entity.Coin
import com.example.cryptowithfragments.domain.entity.CoinResponse
import retrofit2.http.GET

interface CoinApi {
    @GET("/v2/assets")
    suspend fun getCoins(): CoinResponse
}