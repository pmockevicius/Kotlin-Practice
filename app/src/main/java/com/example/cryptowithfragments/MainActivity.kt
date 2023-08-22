package com.example.cryptowithfragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.cryptowithfragments.presentation.coinInfo.ThirdFragment
import com.example.cryptowithfragments.presentation.coinList.SecondFragment
import com.example.cryptowithfragments.presentation.coinSplash.FirstFragment
import com.example.cryptowithfragments.presentation.favoritesList.favoritesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstFragment = FirstFragment()
        val secondFragment = SecondFragment()
        val favoritesFragment = favoritesFragment()

        val bottomNavigationView: BottomNavigationView? = findViewById(R.id.bottomNavigationView);

//        supportFragmentManager.beginTransaction().apply {
//            replace(R.id.flFragment, firstFragment)
//            commit()
//        }

        setCurrentFragment(firstFragment)



        bottomNavigationView?.setOnNavigationItemSelectedListener {
            when (it.itemId){
                R.id.miHome -> setCurrentFragment(firstFragment)
                R.id.miCoins -> setCurrentFragment(secondFragment)
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