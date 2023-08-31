package com.example.cryptowithfragments.presentation.coinList2

import android.util.Log
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

class CoinListViewModel(private val useCase: CoinUseCaseInterface) : CoinListViewModelInterface {
    override val coins: MutableLiveData<List<Coin>> = MutableLiveData<List<Coin>>()
    private val scope = MainScope()
    override var originalCoins : List<Coin> = listOf()



    override suspend fun loadCoins(): List<Coin> {
        return try {
            println("trying in view model")
            originalCoins = useCase.getFinalCoins()
            println("originalCoins in view model $originalCoins")
            originalCoins
        } catch (e: Exception) {
            Log.e("loadCoins()",e.toString())
            emptyList()
        }
    }

    override suspend fun search(name: String): List<Coin> {
        return try {
            useCase.getCoinWithNameFromList(name, originalCoins)


        } catch (e: Exception) {
            println("error")
            emptyList<Coin>()
        }
    }


     override suspend fun saveFavorites(coin: Coin){
         useCase.saveFavorites(coin)
    }

    override suspend fun deleteFavorite(coin: Coin) {
        useCase.deleteFavorite(coin)
    }

    override suspend fun loadfavorites(): List<Coin> {
        return useCase.loadFavorites()
    }

    override suspend fun test(){
        println("test called in viewModel ")
        useCase.test()
    }
}
