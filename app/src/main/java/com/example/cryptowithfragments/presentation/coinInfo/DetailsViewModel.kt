package com.example.cryptowithfragments.presentation.coinInfo

import com.example.cryptowithfragments.domain.usecase.CoinUseCaseInterface
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

interface DetailViewModelInterface {
    fun loadImages()

}

class DetailsViewModel(usecase: CoinUseCaseInterface) : DetailViewModelInterface {

    var usecase: CoinUseCaseInterface = usecase
    private val scope = MainScope()

    override fun loadImages() {
        try {
            scope.launch {
                val result = usecase.getCoinsWithImage()
            }
        } catch (e: Exception) {
            println("error")
        }
    }

}