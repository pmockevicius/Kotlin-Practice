package com.example.cryptowithfragments.presentation.coinList

import androidx.lifecycle.MutableLiveData
import com.example.cryptowithfragments.domain.entity.Coin
import com.example.cryptowithfragments.domain.usecase.CoinUseCaseInterface
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


interface ListViewModelInterface {
    fun load()
    fun changeIsFavorite(coin: Coin)
    fun search(name: String)
    val coins: MutableLiveData<List<Coin>>
    val originalCoins: List<Coin>

    suspend fun saveFavorites(coin: Coin)

    suspend fun deleteFavorite(coin: Coin)

    suspend fun loadfavorites(): List<Coin>
}

class ListViewModel(usecase: CoinUseCaseInterface) : ListViewModelInterface {
    var usecase: CoinUseCaseInterface = usecase
    override val coins: MutableLiveData<List<Coin>> = MutableLiveData<List<Coin>>()
    private val scope = MainScope()
    override var originalCoins : List<Coin> = listOf()

    override fun load() {
            try {
                scope.launch {
                    originalCoins = usecase.getFinalCoins()
                    coins.postValue(originalCoins)
                    println("Origin coin list $originalCoins")
                }
            } catch (e: Exception) {
                println("error")
            }
    }

    override fun search(name: String) {
        try {
            scope.launch {
                val result = usecase.getCoinWithNameFromList(name, originalCoins)
                coins.postValue(result)
            }
        } catch (e: Exception) {
            println("error")
        }
    }

    override fun changeIsFavorite(coin: Coin){
        println("Coin $coin")
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


}
