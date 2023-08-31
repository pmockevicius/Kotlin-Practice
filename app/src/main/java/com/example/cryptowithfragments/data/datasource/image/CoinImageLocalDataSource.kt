package com.example.cryptowithfragments.data.datasource.image

import android.content.Context
import android.content.SharedPreferences
import com.example.cryptowithfragments.domain.entity.Coin
import com.example.cryptowithfragments.domain.entity.Image
import com.example.cryptowithfragments.domain.entity.ImagesWithTimestamp
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.reflect.Type
import java.net.HttpURLConnection
import java.net.URL


class CoinImageLocalDataSource(private val context: Context): CoinImageDataSourceInterface {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
    private val sharedPrefsKey = "coinImages"
    private val gson = Gson()
     override suspend fun getImages(): List<Image> {
        TODO("Not yet implemented")
    }

//     override suspend fun saveImages(imagesToBeSaved:List<Image> ) {
//        val images = loadImages().toMutableList()
//        images.addAll(imagesToBeSaved)
//        val imagesJson = gson.toJson(images)
//        sharedPreferences.edit().putString(sharedPrefsKey, imagesJson).apply()
//
//    }

    override suspend fun saveImages(imagesToBeSaved: List<Image>) {
        val images = loadImages().toMutableList()
        images.addAll(imagesToBeSaved)

        val imagesWithTimestamp = ImagesWithTimestamp(System.currentTimeMillis(), images)

        val imagesJson = gson.toJson(imagesWithTimestamp)
        sharedPreferences.edit().putString(sharedPrefsKey, imagesJson).apply()
    }

     override suspend fun loadImages(): List<Image> {
        val imagesJson = sharedPreferences.getString(sharedPrefsKey, null)
        val type: Type = object : TypeToken<List<Image>>() {}.type
        return if (!imagesJson.isNullOrEmpty()) {
            gson.fromJson(imagesJson, type)
        } else {
            emptyList()
        }
    }

    override suspend fun test(){
        println("im in local image data source")
    }

     override suspend fun loadImagesWithTimestamp(): ImagesWithTimestamp? {
        val imagesJson = sharedPreferences.getString(sharedPrefsKey, null)
        val type: Type = object : TypeToken<ImagesWithTimestamp>() {}.type
        return if (!imagesJson.isNullOrEmpty()) {
            gson.fromJson(imagesJson, type)
        } else {
            null
        }
    }

//     suspend fun deleteFavorite(coin: Coin) {
//        val favorites = loadFavorites().toMutableList()
//        favorites.removeAll { it.id == coin.id }
//        val favoritesJson = gson.toJson(favorites)
//        sharedPreferences.edit().putString(sharedPrefsKey, favoritesJson).apply()
//
//    }


}