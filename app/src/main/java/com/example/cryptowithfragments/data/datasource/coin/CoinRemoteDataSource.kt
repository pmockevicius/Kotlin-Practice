package com.example.cryptowithfragments.data.datasource.coin

import com.example.cryptowithfragments.domain.entity.Coin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL



class CoinRemoteDataSource: CoinDataSourceInterface {
    override suspend fun getCoins(): List<Coin> {
        return withContext(Dispatchers.IO) {
            val url = URL("https://api.coincap.io/v2/assets")
            val connection = url.openConnection() as HttpURLConnection

            val response = try {
                connection.requestMethod = "GET"
                val responseCode = connection.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = connection.inputStream
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    val response = StringBuilder()
                    var line: String?

                    while (reader.readLine().also { line = it } != null) {
                        response.append(line)
                    }

                    reader.close()
                    response.toString()
                } else {
                    throw Exception("API call failed with response code $responseCode")
                }
            } finally {
                connection.disconnect()
            }

            parseApiResponse(apiResponse = response)
        }
    }

    override suspend fun saveFavorite(coin: Coin) {
        TODO("Not yet implemented")
    }

    override suspend fun loadFavorites(): List<Coin> {
        return listOf()
    }

    override suspend fun deleteFavorite(coin: Coin) {
        TODO("Not yet implemented")
    }


    private fun parseApiResponse(apiResponse: String): List<Coin> {
        val jsonObject = JSONObject(apiResponse)
        val dataArray = jsonObject.getJSONArray("data")

        val dataList = mutableListOf<Coin>()

        for (i in 0 until dataArray.length()) {
            val cryptoObject = dataArray.getJSONObject(i)

            val id = cryptoObject.getString("id")
            val rank = cryptoObject.getString("rank")
            val symbol = cryptoObject.getString("symbol")
            val name = cryptoObject.getString("name")
            val supply = cryptoObject.getString("supply")
            val maxSupply = cryptoObject.getString("maxSupply")
            val marketCapUsd = cryptoObject.getString("marketCapUsd")
            val volumeUsd24Hr = cryptoObject.getString("volumeUsd24Hr")
            val priceUsd = "%.2f".format(cryptoObject.getString("priceUsd").toDouble())
            val changePercent24Hr = "%.2f".format(cryptoObject.getString("changePercent24Hr").toDouble())


            dataList.add(Coin(id,rank,symbol, name, supply, maxSupply,marketCapUsd, volumeUsd24Hr,priceUsd, changePercent24Hr))
        }

        return dataList
    }
}