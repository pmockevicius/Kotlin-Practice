package com.example.cryptowithfragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cryptowithfragments.presentation.coinSplash.FirstFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firstFragment = FirstFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, firstFragment)
            commit()
        }
    }

}