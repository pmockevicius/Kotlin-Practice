package com.example.cryptowithfragments.data.datasource.image

import com.example.cryptowithfragments.domain.entity.Coin
import com.example.cryptowithfragments.domain.entity.Image
import com.example.cryptowithfragments.domain.entity.ImagesWithTimestamp

interface CoinImageDataSourceInterface {
    suspend fun getImages(): List<Image>
    suspend fun test()

    suspend fun saveImages(imagesToBeSaved:List<Image> )

    suspend fun loadImagesWithTimestamp(): ImagesWithTimestamp?

    suspend fun loadImages(): List<Image>


}