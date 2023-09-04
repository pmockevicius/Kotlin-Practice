package com.example.cryptowithfragments.data.repository

import androidx.lifecycle.LiveData
import com.example.cryptowithfragments.data.datasource.db.CoinDao
import com.example.cryptowithfragments.domain.entity.Coin

class RoomRepository(private val coinDao: CoinDao) {

    val readAllData: LiveData<List<Coin>> = coinDao.getAllCoins()

    suspend fun addCoin(coins: List<Coin>){
        coinDao.insertAll(coins)
    }
}