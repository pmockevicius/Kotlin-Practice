package com.example.cryptowithfragments.presentation.coinSplash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import com.example.cryptowithfragments.R
import com.example.cryptowithfragments.presentation.coinList.SecondFragment


/**
 * A simple [Fragment] subclass.
 * Use the [FirstFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FirstFragment : Fragment(R.layout.fragment_first) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnFragment22 = view.findViewById<Button>(R.id.btnFragment22)
        btnFragment22.setOnClickListener {
            val secondFragment = SecondFragment() // Create an instance of the SecondFragment
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.flFragment, secondFragment) // Replace current fragment with secondFragment
                addToBackStack(null) // Add the transaction to the back stack for navigation
                commit() // Commit the transaction
            }
        }
    }
}
