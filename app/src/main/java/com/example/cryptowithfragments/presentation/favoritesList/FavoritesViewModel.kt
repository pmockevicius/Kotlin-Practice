package com.example.cryptowithfragments.presentation.favoritesList

import android.os.Bundle
import android.view.View
import com.example.cryptowithfragments.domain.entity.Coin
import com.example.cryptowithfragments.domain.usecase.CoinUseCaseInterface
import com.example.cryptowithfragments.presentation.coinList.ListViewModelInterface
import kotlinx.coroutines.MainScope


interface FavoritesViewModelInterface{
    suspend fun loadfavorites(): List<Coin>

}


class FavoritesViewModel(usecase: CoinUseCaseInterface) : FavoritesViewModelInterface {
    var usecase: CoinUseCaseInterface = usecase
    private val scope = MainScope()
    private lateinit var viewModel: FavoritesViewModel


    override suspend fun loadfavorites(): List<Coin> {
        return usecase.loadFavorites()
    }







}
