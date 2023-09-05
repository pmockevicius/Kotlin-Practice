package com.example.cryptowithfragments.presentation.coinList2

import CoinLocalDataSource
import android.app.Application
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment

import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.cryptowithfragments.R
import com.example.cryptowithfragments.data.datasource.coin.CoinRemoteDataSource
import com.example.cryptowithfragments.data.repository.CoinRepository
import com.example.cryptowithfragments.domain.usecase.CoinUseCase
import com.example.cryptowithfragments.presentation.coinInfo.ThirdFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptowithfragments.data.datasource.coin.CoinDataSourceInterface
import com.example.cryptowithfragments.data.datasource.coin.CoinLocalDbDataSource
import com.example.cryptowithfragments.data.datasource.db.CoinDao
import com.example.cryptowithfragments.data.datasource.image.CoinImageLocalDataSource
import com.example.cryptowithfragments.data.datasource.image.CoinImageRemoteDataSource
import com.example.cryptowithfragments.data.repository.ImageRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


class CoinListFragment : Fragment(R.layout.list_fragment) {

    private lateinit var viewModel: CoinListViewModelInterface
    private lateinit var searchEditText: EditText


    private val scope = MainScope()

    private lateinit var useCase: CoinUseCase
    var remoteDatasource = CoinRemoteDataSource()
    var remoteImageDataSource = CoinImageRemoteDataSource()
    lateinit var localImageDataSource: CoinImageLocalDataSource
    lateinit var localDataSource: CoinDataSourceInterface
    lateinit var repositoryCoin: CoinRepository
    lateinit var repositoryImage: ImageRepository

    private lateinit var adapter: CoinListAdapter
//    private lateinit var coinDao: CoinDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        localImageDataSource = CoinImageLocalDataSource(requireContext())
        localDataSource = CoinLocalDbDataSource(requireContext())



        repositoryCoin =
            CoinRepository(remoteDatasource = remoteDatasource, localDataSource = localDataSource)
        repositoryImage = ImageRepository(
            remoteImageDataSource = remoteImageDataSource,
            localImageDataSource = localImageDataSource
        )
        useCase = CoinUseCase(repositoryCoin = repositoryCoin, repositoryImage = repositoryImage)
        viewModel = CoinListViewModel(useCase, application = Application())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        Glide.with(this).setDefaultRequestOptions(RequestOptions())

        searchEditText = view.findViewById(R.id.searchEditText)

        setupRecyclerView()
        setupRefreshButtonListener()
        setUpSearchListener()

    }

    fun setUpSearchListener() {
        searchEditText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var text = s.toString().lowercase()
                scope.launch {
                    var result = viewModel.search(text)
                    adapter.updateData(result)
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }


    fun setupRefreshButtonListener() {
        val btnRefresh = view?.findViewById<Button>(R.id.btnRefresh)
        if (btnRefresh != null) {
            btnRefresh.setOnClickListener {
                searchEditText?.text?.clear()
                scope.launch {
                    val originalCoins = viewModel.loadCoins()
                    useCase.saveCoinsToRoomDb(originalCoins)
                    adapter.updateData(originalCoins)
                }

            }
        }
    }

    fun setupRecyclerView() {
        context?.let {
            val rvList: RecyclerView? = view?.findViewById(R.id.rvList2)

            val layoutManager = LinearLayoutManager(requireContext())
            adapter = CoinListAdapter(
                emptyList(),
                onItemClick = { clickedCoin ->
                    val thirdFragment = ThirdFragment()
                    val bundle = Bundle()
                    bundle.putSerializable("cryptoInfo", clickedCoin)

                    thirdFragment.arguments = bundle

                    parentFragmentManager.beginTransaction().apply {
                        replace(R.id.flFragment, thirdFragment)
                        addToBackStack(null)
                        commit()
                    }
                },
                onItemFavIconClick = { clickedCoin ->
                    if (clickedCoin.isFavorite) {
                        scope.launch {
                            viewModel.deleteFavorite(clickedCoin)
                            clickedCoin.isFavorite = !clickedCoin.isFavorite
                        }
                    } else {
                        scope.launch {
                            clickedCoin.isFavorite = !clickedCoin.isFavorite
                            viewModel.saveFavorites(clickedCoin)
                        }
                    }

                    scope.launch {
                        val originalCoins = viewModel.loadCoins()
                        adapter.updateData(originalCoins)
                    }
                },
                it
            )

            rvList?.layoutManager = layoutManager
            rvList?.adapter = adapter

            scope.launch {
                val originalCoins = viewModel.loadCoins()
                println("originalCoins in fragment $originalCoins")
                adapter.updateData(originalCoins)
            }
        }
    }


}
