package com.example.cryptowithfragments.data.repository

import com.example.cryptowithfragments.data.datasource.image.CoinImageDataSourceInterface
import com.example.cryptowithfragments.data.datasource.image.CoinImageLocalDataSource
import com.example.cryptowithfragments.domain.entity.Image
import com.example.cryptowithfragments.domain.entity.ImagesWithTimestamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface ImageRepositoryInterface {
    suspend fun getImages(): List<Image>
    suspend fun test()
}

class ImageRepository( remoteImageDataSource: CoinImageDataSourceInterface, localImageDataSource: CoinImageDataSourceInterface) : ImageRepositoryInterface {

    private var remoteImageDataSource: CoinImageDataSourceInterface = remoteImageDataSource
    private var localImageDataSource : CoinImageDataSourceInterface = localImageDataSource


    override suspend fun getImages(): List<Image> {
        val localImages = localImageDataSource.loadImages()
        println("localImages $localImages")
        return if (localImages.isEmpty()) {
            remoteImageDataSource.getImages().also {
                localImageDataSource.saveImages(it)
            }
        } else {
            localImages
        }
    }

//    override suspend fun getImages(): List<Image> {
//        println("get images in repo called ")
//
//        val imagesWithTimestamp: ImagesWithTimestamp? = withContext(Dispatchers.IO) {
//            localImageDataSource.loadImagesWithTimestamp()
//        }
//
//        println("imagesWithTimestamp $imagesWithTimestamp ")
//
//        if (imagesWithTimestamp != null) {
//            val currentTime = System.currentTimeMillis()
//            val lastSavedTime = imagesWithTimestamp.timestamp
//
//            if (currentTime - lastSavedTime <= 24 * 60 * 60 * 1000) { // 24 hours in milliseconds
//                return imagesWithTimestamp.images
//            }
//        }
//
//        return remoteImageDataSource.getImages().also {
//            localImageDataSource.saveImages(it)
//        }
//    }


    override suspend fun test(){
        println("test called in repository ")
        localImageDataSource.test()
    }
}