package com.example.cryptowithfragments.data.repository

import com.example.cryptowithfragments.data.datasource.CoinDataSourceInterface
import com.example.cryptowithfragments.data.datasource.CoinImageDataSourceInterface
import com.example.cryptowithfragments.data.datasource.CoinLocalDataSource
import com.example.cryptowithfragments.data.datasource.CoinLocalDataSourceInterface
import com.example.cryptowithfragments.domain.entity.Coin
import com.example.cryptowithfragments.domain.entity.Image

interface CoinRepositoryInterface {
    suspend fun getCoins(): List<Coin>
}

class CoinRepository(remoteDatasource: CoinDataSourceInterface, localDataSource: CoinLocalDataSourceInterface) : CoinRepositoryInterface {
    private var remoteDatasource: CoinDataSourceInterface = remoteDatasource
    private var localDatasource: CoinLocalDataSourceInterface = localDataSource


    override suspend fun getCoins(): List<Coin> {

        return remoteDatasource.getCoins()
    }

}