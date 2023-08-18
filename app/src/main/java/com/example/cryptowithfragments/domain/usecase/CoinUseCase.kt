package com.example.cryptowithfragments.domain.usecase

import com.example.cryptowithfragments.data.repository.CoinRepositoryInterface
import com.example.cryptowithfragments.data.repository.ImageRepositoryInterface
import com.example.cryptowithfragments.domain.entity.Coin
import com.example.cryptowithfragments.domain.entity.Image


interface CoinUseCaseInterface {
    suspend fun getCoins(): List<Coin>
    suspend fun getImages(): List<Image>
    suspend fun getCoinWithNameFromList(name: String, ofList: List<Coin>): List<Coin>
    suspend fun getCoinsWithImage(): List<Coin>
}

class CoinUseCase(repositoryCoin: CoinRepositoryInterface, repositoryImage: ImageRepositoryInterface) : CoinUseCaseInterface {
    private var repositoryCoin: CoinRepositoryInterface = repositoryCoin
    private var repositoryImage: ImageRepositoryInterface = repositoryImage
    var Images: List<Image> = listOf<Image>()

    override suspend fun getCoins(): List<Coin> {
        return repositoryCoin.getCoins()
    }


    override suspend fun getCoinWithNameFromList(name: String, ofList: List<Coin>): List<Coin> {
        var coins = ofList
        var filteredCoins = coins.filter { it.name.lowercase().contains(name) }
        return filteredCoins
    }

    override suspend fun getImages(): List<Image> {
        return repositoryImage.getImages()
    }

    override suspend fun getCoinsWithImage(): List<Coin> {
        val coins = repositoryCoin.getCoins()
        val images = repositoryImage.getImages()

        val coinsWithImages = coins.map { coin ->
            val matchingImage = images.find { image -> image.id == coin.id
            }
            coin.copy(imageUrl = matchingImage?.image ?: "")
        }

        return coinsWithImages
    }

}