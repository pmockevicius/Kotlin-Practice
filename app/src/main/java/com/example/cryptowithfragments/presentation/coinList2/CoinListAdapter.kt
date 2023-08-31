package com.example.cryptowithfragments.presentation.coinList2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cryptowithfragments.R
import com.example.cryptowithfragments.domain.entity.Coin

class CoinListAdapter(
    var originalCoins: List<Coin>,
    private val onItemClick: (Coin) -> Unit,
    private val onItemFavIconClick: (Coin) -> Unit,
    private val context: Context
) : RecyclerView.Adapter<CoinListAdapter.CoinlistViewHolder>() {

    inner class CoinlistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nameTextView: TextView = itemView.findViewById(R.id.tvName)
        val priceTextView: TextView = itemView.findViewById(R.id.tvPrice)
        val changeTextView: TextView = itemView.findViewById(R.id.tvChange)

        val favoriteIcon: ImageView = itemView.findViewById(R.id.isFavorite)

        val coinImageView: ImageView = itemView.findViewById(R.id.coinLogo)

        init {
            itemView.setOnClickListener {
                onItemClick(originalCoins[adapterPosition])

            }

            favoriteIcon.setOnClickListener {
                onItemFavIconClick(originalCoins[adapterPosition])
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinlistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, null, false)
        return CoinlistViewHolder(view)

    }

    override fun getItemCount(): Int {
        return originalCoins.size

    }

    override fun onBindViewHolder(holder: CoinlistViewHolder, position: Int) {
        val currentCoin = originalCoins[position]
        holder.nameTextView.text = currentCoin.name
        holder.priceTextView.text = "Price USD: $ " + currentCoin.priceUsd
        holder.changeTextView.text = "Price Change 24h " + currentCoin.changePercent24Hr + "%"

        val favoriteIconResource = if (currentCoin.isFavorite) {
            R.drawable.ic_favorites_selected
        } else {
            R.drawable.ic_favorites
        }

        val imageUrl = currentCoin.imageUrl


        holder.favoriteIcon.setImageResource(favoriteIconResource)


        Glide.with(context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_coins)
            .error(R.drawable.ic_coins)
            .into(holder.coinImageView)


    }

    fun updateData(newData: List<Coin>) {
        originalCoins = newData
        notifyDataSetChanged()
    }

    fun removeItemAtPosition(position: Int) {
        val coins = originalCoins.toMutableList()
        coins.removeAt(position)
        originalCoins = coins
        notifyItemRemoved(position)
    }

}
