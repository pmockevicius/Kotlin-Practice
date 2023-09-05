package com.example.cryptowithfragments.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

data class CoinResponse(
    val data: List<Coin>

)

@Entity(tableName = "coins")
data class Coin(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "rank") val rank: String,
    @ColumnInfo(name = "symbol") val symbol: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "supply") val supply: String,
    @ColumnInfo(name = "maxSupply") val maxSupply: String?,
    @ColumnInfo(name = "marketCapUsd") val marketCapUsd: String,
    @ColumnInfo(name = "volumeUsd24Hr") val volumeUsd24Hr: String,
    @ColumnInfo(name = "priceUsd") val priceUsd: String,
    @ColumnInfo(name = "changePercent24Hr") val changePercent24Hr: String,
    @ColumnInfo(name = "imageUrl") val imageUrl: String = "",
    @ColumnInfo(name = "isFavorite") var isFavorite: Boolean = false

) : Serializable