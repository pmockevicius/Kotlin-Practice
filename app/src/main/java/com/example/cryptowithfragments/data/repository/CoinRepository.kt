package com.example.cryptowithfragments.data.repository

import androidx.lifecycle.LiveData
import com.example.cryptowithfragments.data.datasource.coin.CoinDataSourceInterface
import com.example.cryptowithfragments.data.datasource.db.CoinDao

import com.example.cryptowithfragments.domain.entity.Coin

interface CoinRepositoryInterface {
    suspend fun getCoins(): List<Coin>
    suspend fun saveFavorite(coin: Coin)
    suspend fun  loadFavorites(): List<Coin>

    suspend fun deleteFavorite(coin: Coin)

    suspend fun insertCoins(coins: List<Coin>)
    suspend fun getAllCoins(): List<Coin>


}

class CoinRepository(val remoteDatasource: CoinDataSourceInterface, val localDataSource: CoinDataSourceInterface, private val coinDao: CoinDao) : CoinRepositoryInterface {

    // ROOM //


    val readAllData: LiveData<List<Coin>> = coinDao.getAllCoins()

    suspend fun addCoins(coins: List<Coin>){
        coinDao.insertAll(coins)
    }

    //Room//

    override suspend fun getCoins(): List<Coin> {
        return remoteDatasource.getCoins()
    }

    override suspend fun saveFavorite(coin: Coin){
        localDataSource.saveFavorite(coin)
    }

    override suspend fun loadFavorites(): List<Coin> {
        var favoriteCoins = localDataSource.loadFavorites()
        return favoriteCoins
    }

    override suspend fun deleteFavorite(coin: Coin) {
        localDataSource.deleteFavorite(coin)
    }

    override suspend fun insertCoins(coins: List<Coin>) {
//        coinDao.insertAll(coins)
    }

    override suspend fun getAllCoins(): List<Coin> {
        TODO("Not yet implemented")
    }

//    override suspend fun getAllCoins(): List<Coin> {
////        return coinDao.getAllCoins()
//       listOf<Coin>()
//    }


}