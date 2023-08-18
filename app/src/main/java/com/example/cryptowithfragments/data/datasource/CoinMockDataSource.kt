package com.example.cryptowithfragments.data.datasource

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

}