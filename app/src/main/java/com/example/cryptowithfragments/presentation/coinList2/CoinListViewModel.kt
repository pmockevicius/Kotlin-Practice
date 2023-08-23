package com.example.cryptowithfragments.presentation.coinList2

import androidx.lifecycle.MutableLiveData
import com.example.cryptowithfragments.domain.entity.Coin
import com.example.cryptowithfragments.domain.usecase.CoinUseCaseInterface
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


interface CoinListViewModelInterface {
    suspend fun loadCoins(): List<Coin>
    suspend fun search(name: String): List<Coin>
    val coins: MutableLiveData<List<Coin>>
    val originalCoins: List<Coin>

    suspend fun saveFavorites(coin: Coin)

    suspend fun deleteFavorite(coin: Coin)

    suspend fun loadfavorites(): List<Coin>

    suspend fun test()
}

class CoinListViewModel(usecase: CoinUseCaseInterface) : CoinListViewModelInterface {
    var usecase: CoinUseCaseInterface = usecase
    override val coins: MutableLiveData<List<Coin>> = MutableLiveData<List<Coin>>()
    private val scope = MainScope()
    override var originalCoins : List<Coin> = listOf()



    override suspend fun loadCoins(): List<Coin> {
        return try {
            originalCoins = usecase.getFinalCoins()
            originalCoins
        } catch (e: Exception) {
            println("error")
            emptyList()
        }
    }

    override suspend fun search(name: String): List<Coin> {
        return try {
            println("search in viewModel ${usecase.getCoinWithNameFromList(name, originalCoins)} ")

            usecase.getCoinWithNameFromList(name, originalCoins)


        } catch (e: Exception) {
            println("error")
            emptyList<Coin>()
        }
    }


     override suspend fun saveFavorites(coin: Coin){
         usecase.saveFavorites(coin)
    }

    override suspend fun deleteFavorite(coin: Coin) {
        usecase.deleteFavorite(coin)
    }

    override suspend fun loadfavorites(): List<Coin> {
        return usecase.loadFavorites()
    }

    override suspend fun test(){
        println("test called in viewModel ")
        usecase.test()
    }



}
