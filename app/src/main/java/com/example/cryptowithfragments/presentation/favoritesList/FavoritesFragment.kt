package com.example.cryptowithfragments.presentation.favoritesList

import CoinLocalDataSource
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Dao
import com.example.cryptowithfragments.R
import com.example.cryptowithfragments.data.datasource.coin.CoinRemoteDataSource
import com.example.cryptowithfragments.data.datasource.coin.CoinRemoteDataSourceWithRetro
import com.example.cryptowithfragments.data.datasource.db.CoinDao
import com.example.cryptowithfragments.data.datasource.image.CoinImageLocalDataSource
import com.example.cryptowithfragments.data.datasource.image.CoinImageRemoteDataSource
import com.example.cryptowithfragments.data.repository.CoinRepository
import com.example.cryptowithfragments.data.repository.ImageRepository
import com.example.cryptowithfragments.domain.usecase.CoinUseCase
import com.example.cryptowithfragments.presentation.coinInfo.ThirdFragment
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class FavoritesFragment : Fragment(R.layout.favorites_fragment) {

    private lateinit var viewModel: FavoritesViewModelInterface
    private val scope = MainScope()


    private lateinit var usecase: CoinUseCase
    lateinit var localImageDataSource: CoinImageLocalDataSource
    private lateinit var localDataSource: CoinLocalDataSource
    private lateinit var repositoryCoin: CoinRepository
    private lateinit var repositoryImage: ImageRepository
    private lateinit var adapter: FavoriteCoinAdapter
    private lateinit var coinDao: CoinDao

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDependencies()
        setupViewModel()
        setupRecyclerView()

    }

    private fun setupDependencies() {
        localImageDataSource = CoinImageLocalDataSource(requireContext())
        localDataSource = CoinLocalDataSource(requireContext())
        repositoryCoin = CoinRepository(
            remoteDatasource = CoinRemoteDataSource(),
            localDataSource = localDataSource,


        )
        repositoryImage = ImageRepository(remoteImageDataSource = CoinImageRemoteDataSource(), localImageDataSource = localImageDataSource)
        usecase = CoinUseCase(repositoryCoin = repositoryCoin, repositoryImage = repositoryImage)
    }

    private fun setupViewModel() {
        viewModel = FavoritesViewModel(usecase = usecase)
    }

    fun setupRecyclerView() {
        context?.let {
            val rvList: RecyclerView? = view?.findViewById(R.id.rvList)

            val layoutManager = LinearLayoutManager(requireContext())
            adapter = FavoriteCoinAdapter(
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
                    scope.launch {
                        viewModel.deleteFavorite(clickedCoin)
                    }

                    scope.launch {
                        var favoriteCoins = viewModel.loadfavorites()
                        adapter.updateData(favoriteCoins)
                    }
                }, it
            )

            if (rvList != null) {
                rvList.layoutManager = layoutManager
            }
            if (rvList != null) {
                rvList.adapter = adapter
            }

            scope.launch {
                val favoriteCoins = viewModel.loadfavorites()
                adapter.updateData(favoriteCoins)
            }
        }

    }
}
