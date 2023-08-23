package com.example.cryptowithfragments.presentation.coinList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cryptowithfragments.R
import com.example.cryptowithfragments.domain.entity.Coin

class listViewAdapter(
    var favoriteCoins: List<Coin>,
    private val onItemClick: (Coin) -> Unit,
    private val onItemFavIconClick: (Coin) -> Unit

): RecyclerView.Adapter<listViewAdapter.listViewHolder>() {

    inner class listViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val nameTextView: TextView = itemView.findViewById(R.id.tvName)
        val priceTextView: TextView = itemView.findViewById(R.id.tvPrice)
        val changeTextView: TextView = itemView.findViewById(R.id.tvChange)

        val favoriteIcon: ImageView = itemView.findViewById(R.id.isFavorite)

        init {
            itemView.setOnClickListener {
                onItemClick(favoriteCoins[adapterPosition])

                println(favoriteCoins[adapterPosition])
            }

            favoriteIcon.setOnClickListener{
                onItemFavIconClick(favoriteCoins[adapterPosition])
                println("hey hey ${favoriteCoins[adapterPosition]}")
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): listViewHolder {
        val view =  LayoutInflater.from(parent.context).inflate(R.layout.favorite_crypto, null, false)
        return listViewHolder(view)

        println("Im in adaptor")
    }

    override fun getItemCount(): Int {
        return favoriteCoins.size

    }

    override fun onBindViewHolder(holder: listViewHolder, position: Int) {
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
