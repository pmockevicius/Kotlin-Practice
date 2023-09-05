package com.example.cryptowithfragments.data.datasource.coin

import com.example.cryptowithfragments.data.api.RetrofitFactory
import com.example.cryptowithfragments.domain.entity.Coin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CoinRemoteDataSourceWithRetro: CoinDataSourceInterface {
    private val service = RetrofitFactory.makeRetrofitService();

    override suspend fun getCoins(): List<Coin> {

        return withContext(Dispatchers.IO) {
            service.getCoins().data
        }
    }

    override suspend fun saveCoins(coins: List<Coin>) {
        TODO("Not yet implemented")
    }

    override suspend fun saveFavorite(coin: Coin) {
        TODO("Not yet implemented")
    }

    override suspend fun loadFavorites(): List<Coin> {
        return listOf()
    }

    override suspend fun deleteFavorite(coin: Coin) {
        TODO("Not yet implemented")
    }
}