package com.example.cryptowithfragments.data.datasource.coin

import com.example.cryptowithfragments.data.datasource.coin.CoinDataSourceInterface
import com.example.cryptowithfragments.domain.entity.Coin

class CoinMockDataSource: CoinDataSourceInterface {
    override suspend fun getCoins(): List<Coin> {
        var id = "example"
        var rank = "example"
        var symbol = "example"
        var name = "example"
        var supply = "example"
        var maxSupply = "example"
        var marketCapUsd = "example"
        var volumeUsd24Hr = "example"
        var priceUsd = "example"
        var changePercent24Hr = "example"

        return listOf(Coin(id,rank,symbol, name, supply, maxSupply,marketCapUsd, volumeUsd24Hr,priceUsd, changePercent24Hr))
    }

    override suspend fun saveCoins(coins: List<Coin>) {
        TODO("Not yet implemented")
    }

    override suspend fun saveFavorite(coin: Coin) {
        TODO("Not yet implemented")
    }

    override suspend fun loadFavorites(): List<Coin> {
        // Implement the loadFavorites logic here and return the list of favorite coins
        return listOf()
    }

    override suspend fun deleteFavorite(coin: Coin) {
        TODO("Not yet implemented")
    }



}