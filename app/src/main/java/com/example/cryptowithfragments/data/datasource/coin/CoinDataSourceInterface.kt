package com.example.cryptowithfragments.data.datasource.coin

import com.example.cryptowithfragments.domain.entity.Coin

interface CoinDataSourceInterface {
    suspend fun getCoins(): List<Coin>
    suspend fun saveFavorite(coin: Coin)
    suspend fun deleteFavorite(coin: Coin)
    suspend fun loadFavorites(): List<Coin>


}