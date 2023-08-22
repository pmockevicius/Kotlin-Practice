package com.example.cryptowithfragments.data.repository

import com.example.cryptowithfragments.data.datasource.coin.CoinDataSourceInterface

import com.example.cryptowithfragments.domain.entity.Coin

interface CoinRepositoryInterface {
    suspend fun getCoins(): List<Coin>
    suspend fun saveFavorite(coin: Coin)
    suspend fun  loadFavorites(): List<Coin>

    suspend fun deleteFavorite(coin: Coin)


}

class CoinRepository(remoteDatasource: CoinDataSourceInterface, localDataSource: CoinDataSourceInterface) : CoinRepositoryInterface {
    private var remoteDatasource: CoinDataSourceInterface = remoteDatasource
    private var localDatasource: CoinDataSourceInterface = localDataSource


    override suspend fun getCoins(): List<Coin> {
        return remoteDatasource.getCoins()
    }

    override suspend fun saveFavorite(coin: Coin){
        println("saveFavorites in repository")
        localDatasource.saveFavorite(coin)
    }

    override suspend fun loadFavorites(): List<Coin> {
        var favoriteCoins = localDatasource.loadFavorites()
        return favoriteCoins
    }

    override suspend fun deleteFavorite(coin: Coin) {
        localDatasource.deleteFavorite(coin)
    }


}