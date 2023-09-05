package com.example.cryptowithfragments.data.datasource.coin

import android.content.Context
import com.example.cryptowithfragments.data.datasource.db.AppDatabase
import com.example.cryptowithfragments.data.datasource.db.CoinDao
import com.example.cryptowithfragments.domain.entity.Coin


class CoinLocalDbDataSource(context: Context) : CoinDataSourceInterface {

    private var coinDao: CoinDao

    init {
        coinDao = AppDatabase.getDatabase(context).coinDao()
    }

    override suspend fun getCoins(): List<Coin> {
        TODO("Not yet implemented")
    }

    override suspend fun saveCoins(coins: List<Coin>) {
        TODO("Not yet implemented")
    }

    override suspend fun saveFavorite(coin: Coin) {
        coinDao.insert(coin)
    }

    override suspend fun deleteFavorite(coin: Coin) {
        TODO("Not yet implemented")
    }

    override suspend fun loadFavorites(): List<Coin> {
        return coinDao.getAllCoins()
    }
}