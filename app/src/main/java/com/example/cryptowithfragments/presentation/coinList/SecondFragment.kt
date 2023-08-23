package com.example.cryptowithfragments.presentation.coinList

import CoinLocalDataSource
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import androidx.fragment.app.Fragment

import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import com.example.cryptowithfragments.R
import com.example.cryptowithfragments.data.datasource.coin.CoinRemoteDataSource
import com.example.cryptowithfragments.data.repository.CoinRepository
import com.example.cryptowithfragments.domain.usecase.CoinUseCase
import com.example.cryptowithfragments.presentation.coinInfo.ThirdFragment
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptowithfragments.data.datasource.image.CoinImageLocalDataSource
import com.example.cryptowithfragments.data.datasource.image.CoinImageRemoteDataSource
import com.example.cryptowithfragments.data.repository.ImageRepository
import com.example.cryptowithfragments.domain.entity.Coin
import com.example.cryptowithfragments.presentation.favoritesList.favoriteCoinAdapter
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class SecondFragment : Fragment(R.layout.fragment_second) {

    private lateinit var viewModel: ListViewModelInterface


    private val scope = MainScope()

    private lateinit var usecase: CoinUseCase
    var remoteDatasource = CoinRemoteDataSource()
    var remoteImageDataSource = CoinImageRemoteDataSource()
    lateinit var localImageDataSource: CoinImageLocalDataSource
    lateinit var localDataSource: CoinLocalDataSource
    lateinit var repositoryCoin: CoinRepository
    lateinit var repositoryImage: ImageRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        localImageDataSource= CoinImageLocalDataSource(requireContext())
        localDataSource = CoinLocalDataSource(requireContext())
        repositoryCoin =
            CoinRepository(remoteDatasource = remoteDatasource, localDataSource = localDataSource)
        repositoryImage = ImageRepository(remoteImageDataSource = remoteImageDataSource, localImageDataSource = localImageDataSource)
        usecase = CoinUseCase(repositoryCoin = repositoryCoin, repositoryImage = repositoryImage)


        viewModel = ListViewModel(usecase = usecase)

        viewModel.load()

        //Recycler View

//
//        var favoriteCoins: List<Coin> = listOf()
//
//        val rvList: RecyclerView = view.findViewById(R.id.rvList)
//
//
//        val layoutManager = LinearLayoutManager(requireContext())
//        val adapter = favoriteCoinAdapter(emptyList()){ clickedCoin ->
//            val thirdFragment = ThirdFragment()
//
//            val bundle = Bundle()
//            bundle.putSerializable("cryptoInfo", clickedCoin)
//
//            thirdFragment.arguments = bundle
//
//
//            parentFragmentManager.beginTransaction().apply {
//                replace(R.id.flFragment, thirdFragment)
//                addToBackStack(null)
//                commit()
//            }
//
//        }
//
//        rvList.layoutManager = layoutManager
//        rvList.adapter = adapter
//
//        scope.launch {
//            favoriteCoins = viewModel.loadfavorites()
//            adapter.updateData(favoriteCoins)
//        }




        //Recycler View


        val searchEditText = view.findViewById<EditText>(R.id.searchEditText)

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var text = s.toString().lowercase()
                viewModel.search(text)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        val btnRefresh = view.findViewById<Button>(R.id.btnRefresh)
        btnRefresh.setOnClickListener {
            searchEditText.text.clear()
            viewModel.load()

        }

        val linearLayout = view.findViewById<LinearLayout>(R.id.apiResponseTextView)

        viewModel.coins.observe(viewLifecycleOwner, Observer { cryptoInfoList ->
            linearLayout.removeAllViews()

            fun setFavoriteIconResource(cryptoInfo: Coin): Int{
               var favoriteIconResource = if (cryptoInfo.isFavorite) {
                    R.drawable.filled
                } else {
                    R.drawable.star
                }
                return favoriteIconResource

            }

            for (cryptoInfo in cryptoInfoList) {

                //Favorite icon

                val favoriteIconView = ImageView(requireContext())
                favoriteIconView.layoutParams = LinearLayout.LayoutParams(
                    resources.getDimensionPixelSize(R.dimen.icon_size),
                    resources.getDimensionPixelSize(R.dimen.icon_size)
                )
//
                favoriteIconView.setImageResource(setFavoriteIconResource(cryptoInfo))

                favoriteIconView.setOnClickListener {
                    scope.launch {
                        cryptoInfo.isFavorite = !cryptoInfo.isFavorite

                        if (cryptoInfo.isFavorite) {
                            viewModel.saveFavorites(cryptoInfo)
                        } else {
                            viewModel.deleteFavorite(cryptoInfo)
                        }

                        favoriteIconView.setImageResource(setFavoriteIconResource(cryptoInfo))
                    }

//                    scope.launch {
//                        favoriteCoins = viewModel.loadfavorites()
//                        adapter.updateData(favoriteCoins)
//                    }
                }

                val cryptoInfoLayout = LinearLayout(requireContext())
                cryptoInfoLayout.gravity = Gravity.END
                cryptoInfoLayout.layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                cryptoInfoLayout.addView(favoriteIconView)
                linearLayout.addView(cryptoInfoLayout)


                val changePercent24Hr = cryptoInfo.changePercent24Hr + "%"



                val propertyString = buildString {
                    append( cryptoInfo.name,"\n")
                    append("$",cryptoInfo.priceUsd,"\n")


                    val priceChangeSpannable = SpannableString(changePercent24Hr)
                    val color = if (changePercent24Hr.startsWith("-")) {
                        android.graphics.Color.RED
                    } else {
                        android.graphics.Color.GREEN
                    }

                    priceChangeSpannable.setSpan(
                        ForegroundColorSpan(color),
                        0,
                        priceChangeSpannable.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )

                    append(priceChangeSpannable)
                    append("\n")
                }

                val propertyTextView = TextView(requireContext())
                propertyTextView.text = propertyString
                propertyTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28f)

                propertyTextView.setOnClickListener {
                    searchEditText.text.clear()
                    Toast.makeText(
                        requireContext(),
                        "${cryptoInfo.name} details",
                        Toast.LENGTH_SHORT
                    ).show()

                    val thirdFragment = ThirdFragment()

                    val bundle = Bundle()
                    bundle.putSerializable("cryptoInfo", cryptoInfo)
//
                    thirdFragment.arguments = bundle


                    parentFragmentManager.beginTransaction().apply {
                        replace(R.id.flFragment, thirdFragment)
                        addToBackStack(null)
                        commit()
                    }
                }

                linearLayout.addView(propertyTextView)
            }
        })
    }
}
