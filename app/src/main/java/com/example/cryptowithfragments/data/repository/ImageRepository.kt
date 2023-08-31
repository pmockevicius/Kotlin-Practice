package com.example.cryptowithfragments.data.repository

import com.example.cryptowithfragments.data.datasource.image.CoinImageDataSourceInterface
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


//    override suspend fun getImages(): List<Image> {
//        val localImages = localImageDataSource.loadImages()
//        println("localImages $localImages")
//        return if (localImages.isEmpty()) {
//            remoteImageDataSource.getImages().also {
//                localImageDataSource.saveImages(it)
//            }
//        } else {
//            localImages
//        }
//    }

    override suspend fun getImages(): List<Image> {

        var imagesWithTimestamp: ImagesWithTimestamp? = null

        try {
            imagesWithTimestamp = withContext(Dispatchers.IO) {
                localImageDataSource.loadImagesWithTimestamp()
            }
        } catch (e: Exception) {
            println(e)
        }
        if (imagesWithTimestamp != null) {
            val currentTime = System.currentTimeMillis()
            val lastSavedTime = imagesWithTimestamp.timestamp

            println("we are here 1")
            if (currentTime - lastSavedTime <= 72 * 60 * 60 * 1000) {
                return imagesWithTimestamp.images
            }
        }
        println("we are in else")
        val remoteImages = remoteImageDataSource.getImages()
        localImageDataSource.saveImages(remoteImages)
        return remoteImages

    }

    override suspend fun test(){
        println("test called in repository ")
        localImageDataSource.test()
    }
}