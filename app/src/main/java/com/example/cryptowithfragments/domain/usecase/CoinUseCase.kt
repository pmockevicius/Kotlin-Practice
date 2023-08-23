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
    suspend fun saveFavorites(coin: Coin)

    suspend fun loadFavorites(): List<Coin>

    suspend fun addSavedIsFavoriteStatus(): List<Coin>
    suspend fun getFinalCoins(): List<Coin>

    suspend fun deleteFavorite(coin: Coin)
    suspend fun test ()

}

class CoinUseCase(repositoryCoin: CoinRepositoryInterface, repositoryImage: ImageRepositoryInterface) : CoinUseCaseInterface {
    private var repositoryCoin: CoinRepositoryInterface = repositoryCoin
    private var repositoryImage: ImageRepositoryInterface = repositoryImage
    var Images: List<Image> = listOf<Image>()

    override suspend fun getCoins(): List<Coin> {
        return repositoryCoin.getCoins()
    }


    override suspend fun getCoinWithNameFromList(name: String, ofList: List<Coin>): List<Coin> {
        println("name of coin in usecase ${name}")
        println("ofList in usecase ${ofList}")

        var coins = ofList
        var filteredCoins = coins.filter { it.name.lowercase().contains(name) }
        println("filtered coins from usecase $filteredCoins")
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

        loadFavorites()
        return coinsWithImages
    }

    override suspend fun saveFavorites(coin: Coin){
        repositoryCoin.saveFavorite(coin)
    }

    override suspend fun loadFavorites(): List<Coin> {
        var favoriteCoins = repositoryCoin.loadFavorites()
        return favoriteCoins
    }

    override suspend fun addSavedIsFavoriteStatus(): List<Coin> {
        val coins = getCoinsWithImage()
        val favorites = loadFavorites()

        coins.forEach { coin ->
            if (favorites.any { it.id == coin.id }) {
                coin.isFavorite = true
            }
        }
        return coins
    }

    override suspend fun getFinalCoins(): List<Coin> {
        return addSavedIsFavoriteStatus()
    }

    override suspend fun deleteFavorite(coin: Coin) {
        repositoryCoin.deleteFavorite(coin)
    }

    override suspend fun test (){
        println("test called in usecase ")
        repositoryImage.test()
    }

}