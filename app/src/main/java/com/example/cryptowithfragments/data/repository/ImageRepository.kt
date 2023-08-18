package com.example.cryptowithfragments.data.repository

import com.example.cryptowithfragments.data.datasource.CoinDataSourceInterface
import com.example.cryptowithfragments.data.datasource.CoinImageDataSourceInterface
import com.example.cryptowithfragments.domain.entity.Coin
import com.example.cryptowithfragments.domain.entity.Image

interface ImageRepositoryInterface {
    suspend fun getImages(): List<Image>
}

class ImageRepository( remoteImageDataSource: CoinImageDataSourceInterface) : ImageRepositoryInterface {

    private var remoteImageDataSource: CoinImageDataSourceInterface = remoteImageDataSource


    override suspend fun getImages(): List<Image> {
        return remoteImageDataSource.getImages()
    }
}