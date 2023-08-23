package com.example.cryptowithfragments.domain.entity

data class ImagesWithTimestamp(
    val timestamp: Long,
    val images: List<Image>
)
