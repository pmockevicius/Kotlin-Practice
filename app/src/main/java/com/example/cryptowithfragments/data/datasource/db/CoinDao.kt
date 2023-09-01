package com.example.cryptowithfragments.data.datasource.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.cryptowithfragments.domain.entity.Coin

@Dao
interface CoinDao {
    @Insert
    suspend fun insert(coin: Coin)

    @Insert
    suspend fun insertAll(coins: List<Coin>)



    @Query("SELECT * FROM coins")
    suspend fun getAllCoins(): List<Coin>
}