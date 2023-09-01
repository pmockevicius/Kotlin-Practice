package com.example.cryptowithfragments.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

data class CoinResponse(
    val data: List<Coin>

)
@Entity(tableName = "coins")
data class Coin  (
    @PrimaryKey val id: String,
    val rank: String,
    val symbol: String,
    val name: String,
    val supply: String,
    val maxSupply: String?,
    val marketCapUsd: String,
    val volumeUsd24Hr: String,
    val priceUsd: String,
    val changePercent24Hr: String,
    val imageUrl: String = "",
    var isFavorite: Boolean = false



) : Serializable