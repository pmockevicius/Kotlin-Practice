package com.example.cryptowithfragments.presentation.favoritesList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptowithfragments.R
import com.example.cryptowithfragments.domain.entity.Coin

class favoriteCoinAdapter(
    var favoriteCoins: List<Coin>,
    private val onItemClick: (Coin) -> Unit

): RecyclerView.Adapter<favoriteCoinAdapter.favoriteCoinViewHolder>() {

    inner class favoriteCoinViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val nameTextView: TextView = itemView.findViewById(R.id.tvName)
        val priceTextView: TextView = itemView.findViewById(R.id.tvPrice)
        val changeTextView: TextView = itemView.findViewById(R.id.tvChange)

        init {
            itemView.setOnClickListener {
                onItemClick(favoriteCoins[adapterPosition])

                println(favoriteCoins[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): favoriteCoinViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.favorite_crypto, null, false)
        return favoriteCoinViewHolder(view)

        println("Im in adaptor")
    }

    override fun getItemCount(): Int {
        return favoriteCoins.size

    }

    override fun onBindViewHolder(holder: favoriteCoinViewHolder, position: Int) {
        val currentCoin = favoriteCoins[position]
        holder.nameTextView.text = currentCoin.name
        holder.priceTextView.text = "Price USD: $ "+currentCoin.priceUsd
        holder.changeTextView.text = "Price Change 24h " +currentCoin.changePercent24Hr+"%"


    }

    fun updateData(newData: List<Coin>) {
        favoriteCoins = newData
        notifyDataSetChanged()
    }

}
