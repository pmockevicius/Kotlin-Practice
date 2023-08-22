package com.example.cryptowithfragments.presentation.favoritesList

import CoinLocalDataSource
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptowithfragments.R
import com.example.cryptowithfragments.data.datasource.coin.CoinRemoteDataSource
import com.example.cryptowithfragments.data.datasource.image.CoinImageRemoteDataSource
import com.example.cryptowithfragments.data.repository.CoinRepository
import com.example.cryptowithfragments.data.repository.ImageRepository
import com.example.cryptowithfragments.domain.entity.Coin
import com.example.cryptowithfragments.domain.usecase.CoinUseCase
import com.example.cryptowithfragments.presentation.coinInfo.ThirdFragment
import com.example.cryptowithfragments.presentation.coinList.ListViewModel
import com.example.cryptowithfragments.presentation.coinList.ListViewModelInterface
import com.example.cryptowithfragments.presentation.coinList.SecondFragment
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [favoritesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class favoritesFragment : Fragment(R.layout.favorites_fragment) {

    private lateinit var viewModel: FavoritesViewModelInterface


    private val scope = MainScope()

    private lateinit var usecase: CoinUseCase
    var remoteDatasource = CoinRemoteDataSource()
    var remoteImageDataSource = CoinImageRemoteDataSource()
    lateinit var localDataSource: CoinLocalDataSource
    lateinit var repositoryCoin: CoinRepository
    lateinit var repositoryImage: ImageRepository

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        localDataSource = CoinLocalDataSource(requireContext())
        repositoryCoin =
            CoinRepository(remoteDatasource = remoteDatasource, localDataSource = localDataSource)
        repositoryImage = ImageRepository(remoteImageDataSource = remoteImageDataSource)
        usecase = CoinUseCase(repositoryCoin = repositoryCoin, repositoryImage = repositoryImage)

        viewModel = FavoritesViewModel(usecase = usecase)


        //Recycler View


        var favoriteCoins: List<Coin> = listOf()

        val rvList: RecyclerView = view.findViewById(R.id.rvList)


        val layoutManager = LinearLayoutManager(requireContext())
        val adapter = favoriteCoinAdapter(emptyList()){ clickedCoin ->
            val thirdFragment = ThirdFragment()

            val bundle = Bundle()
            bundle.putSerializable("cryptoInfo", clickedCoin)

            thirdFragment.arguments = bundle


            parentFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, thirdFragment)
                addToBackStack(null)
                commit()
            }

        }

        rvList.layoutManager = layoutManager
        rvList.adapter = adapter

        scope.launch {
            favoriteCoins = viewModel.loadfavorites()
            adapter.updateData(favoriteCoins)
        }

    }
}
