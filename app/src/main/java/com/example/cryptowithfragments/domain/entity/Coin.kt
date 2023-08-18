package com.example.cryptowithfragments.domain.entity

import java.io.Serializable

data class Coin  (
    val id: String,
    val rank: String,
    val symbol: String,
    val name: String,
    val supply: String,
    val maxSupply: String,
    val marketCapUsd: String,
    val volumeUsd24Hr: String,
    val priceUsd: String,
    val changePercent24Hr: String,
    val imageUrl: String = "",
    var isFavorite: Boolean = false



) : Serializable