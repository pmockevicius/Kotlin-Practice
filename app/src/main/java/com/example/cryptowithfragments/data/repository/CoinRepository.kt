package com.example.cryptowithfragments.data.repository

import com.example.cryptowithfragments.data.datasource.CoinDataSourceInterface
import com.example.cryptowithfragments.data.datasource.CoinImageDataSourceInterface
import com.example.cryptowithfragments.domain.entity.Coin
import com.example.cryptowithfragments.domain.entity.Image

interface CoinRepositoryInterface {
    suspend fun getCoins(): List<Coin>
//    suspend fun getImages(): List<Image>
}

//class CoinRepository(remoteDatasource: CoinDataSourceInterface, mockDatasource: CoinDataSourceInterface, remoteImageDataSource: CoinImageDataSourceInterface) : CoinRepositoryInterface {
class CoinRepository(remoteDatasource: CoinDataSourceInterface, mockDatasource: CoinDataSourceInterface) : CoinRepositoryInterface {
    private var mockDatasource: CoinDataSourceInterface = mockDatasource
    private var remoteDatasource: CoinDataSourceInterface = remoteDatasource
//    private var remoteImageDataSource: CoinImageDataSourceInterface = remoteImageDataSource

    override suspend fun getCoins(): List<Coin> {
        return remoteDatasource.getCoins()
    }

//    override suspend fun getImages(): List<Image> {
//        return remoteImageDataSource.getImages()
//    }
}