package com.example.cryptowithfragments.presentation.coinList

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
import com.example.cryptowithfragments.data.datasource.CoinMockDataSource
import com.example.cryptowithfragments.data.datasource.CoinRemoteDataSource
import com.example.cryptowithfragments.data.repository.CoinRepository
import com.example.cryptowithfragments.domain.usecase.CoinUseCase
import com.example.cryptowithfragments.presentation.coinInfo.ThirdFragment
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.widget.ImageView
import com.example.cryptowithfragments.data.datasource.CoinImageRemoteDataSource
import com.example.cryptowithfragments.data.repository.ImageRepository


class SecondFragment : Fragment(R.layout.fragment_second) {

    private lateinit var viewModel: ListViewModelInterface
    var remoteDatasource = CoinRemoteDataSource()
    var mockDatasource = CoinMockDataSource()
    var remoteImageDataSource = CoinImageRemoteDataSource()
    var repositoryCoin = CoinRepository(remoteDatasource = remoteDatasource)
    var repositoryImage = ImageRepository(remoteImageDataSource = remoteImageDataSource)
    var usecase = CoinUseCase(repositoryCoin = repositoryCoin, repositoryImage = repositoryImage )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ListViewModel(usecase = usecase)

        viewModel.load()

//        viewModel.loadImages()

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

        val btnFragment11 = view.findViewById<Button>(R.id.btnRefresh)
        btnFragment11.setOnClickListener {
            searchEditText.text.clear()
            viewModel.load()

        }

        val linearLayout = view.findViewById<LinearLayout>(R.id.apiResponseTextView)

        viewModel.coins.observe(viewLifecycleOwner, Observer { cryptoInfoList ->
            linearLayout.removeAllViews()

            for (cryptoInfo in cryptoInfoList) {

                //Favorite icon

                val favoriteIconView = ImageView(requireContext())
                favoriteIconView.layoutParams = LinearLayout.LayoutParams(
                    resources.getDimensionPixelSize(R.dimen.icon_size), // Set your desired icon size
                    resources.getDimensionPixelSize(R.dimen.icon_size)
                )
                var favoriteIconResource = if (cryptoInfo.isFavorite) {
                    R.drawable.filled
                } else {
                    R.drawable.star
                }
                favoriteIconView.setImageResource(favoriteIconResource)

                favoriteIconView.setOnClickListener {
                    cryptoInfo.isFavorite = !cryptoInfo.isFavorite

                    var favoriteIconResource = if (cryptoInfo.isFavorite) {
                        R.drawable.filled
                    } else {
                        R.drawable.star
                    }
                    favoriteIconView.setImageResource(favoriteIconResource)

                    viewModel.changeIsFavorite(cryptoInfo)
                    println("i was pressed ${cryptoInfo.name}")
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
                    append("Name: ${cryptoInfo.name}\n")
                    append("Price: $${cryptoInfo.priceUsd}\n")
                    append("Change Percent: ")

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
//                propertyTextView.isClickable = true
                propertyTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 28f)

                propertyTextView.setOnClickListener {
                    searchEditText.text.clear()
                    Toast.makeText(requireContext(), "${cryptoInfo.name} details", Toast.LENGTH_SHORT).show()

                    val thirdFragment = ThirdFragment()

                    val bundle = Bundle()
                    bundle.putSerializable("cryptoInfo",cryptoInfo)
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
