package com.example.cryptowithfragments.data.datasource

import com.example.cryptowithfragments.domain.entity.Coin


interface CoinLocalDataSourceInterface {
    suspend fun saveFavorites()
    suspend fun loadFavorites()
}
class CoinLocalDataSource: CoinLocalDataSourceInterface {

    override suspend  fun saveFavorites(){

    }

    override suspend  fun loadFavorites(){

    }


}