package com.example.cryptowithfragments.presentation.coinInfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.cryptowithfragments.R
import com.example.cryptowithfragments.data.datasource.CoinImageRemoteDataSource
import com.example.cryptowithfragments.data.datasource.CoinMockDataSource
import com.example.cryptowithfragments.data.datasource.CoinRemoteDataSource
import com.example.cryptowithfragments.data.repository.CoinRepository
import com.example.cryptowithfragments.domain.entity.Coin
import com.example.cryptowithfragments.domain.usecase.CoinUseCase
import com.example.cryptowithfragments.presentation.coinList.ListViewModel

class ThirdFragment : Fragment(R.layout.fragment_third) {

    private lateinit var viewModel: DetailViewModelInterface

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val cryptoInfo = arguments?.getSerializable("cryptoInfo") as Coin

        val cryptoInfoTextView = view.findViewById<TextView>(R.id.tvThirdFragment)

        val text = """
        Name: ${cryptoInfo.name}
        Price: ${cryptoInfo.priceUsd}
        Change Percent: ${cryptoInfo.changePercent24Hr}
        Id: ${cryptoInfo.id}
        Rank: ${cryptoInfo.rank}
        Symbol: ${cryptoInfo.symbol}
        Supply: ${cryptoInfo.supply}
        Max Supply: ${cryptoInfo.maxSupply}
        Market Cap: ${cryptoInfo.marketCapUsd}
        Volume 24h: ${cryptoInfo.volumeUsd24Hr}
    """.trimIndent()

        cryptoInfoTextView.text = text


        val coinImageView: ImageView = view.findViewById(R.id.coinImageView)
        val imageUrl = cryptoInfo.imageUrl

        Glide.with(this)
            .load(imageUrl)
            .into(coinImageView)

    }
}

