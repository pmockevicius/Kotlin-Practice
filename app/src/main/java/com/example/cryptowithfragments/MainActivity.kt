package com.example.cryptowithfragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.cryptowithfragments.presentation.coinInfo.ThirdFragment
import com.example.cryptowithfragments.presentation.coinList.SecondFragment
import com.example.cryptowithfragments.presentation.coinList2.coinListFragment
import com.example.cryptowithfragments.presentation.coinSplash.FirstFragment
import com.example.cryptowithfragments.presentation.favoritesList.FavoritesFragment

import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstFragment = FirstFragment()
        val secondFragment = SecondFragment()
        val favoritesFragment = FavoritesFragment()
        val coinListFragment = coinListFragment()

        val bottomNavigationView: BottomNavigationView? = findViewById(R.id.bottomNavigationView);

        setCurrentFragment(firstFragment)



        bottomNavigationView?.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.miHome -> setCurrentFragment(firstFragment)
                R.id.miCoins -> setCurrentFragment(coinListFragment)
                R.id.miFavorites -> setCurrentFragment(favoritesFragment)
            }
            true
        }

    }

    private fun setCurrentFragment(fragment: Fragment ) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            addToBackStack(null)
            commit()
        }


}