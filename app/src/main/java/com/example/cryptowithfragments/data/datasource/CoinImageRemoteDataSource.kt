package com.example.cryptowithfragments.data.datasource

import com.example.cryptowithfragments.domain.entity.Image
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


interface CoinImageDataSourceInterface {
    suspend fun getImages(): List<Image>
}

class CoinImageRemoteDataSource: CoinImageDataSourceInterface {
    override suspend fun getImages(): List<Image> {
        return withContext(Dispatchers.IO) {
            val url = URL("https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=50&page=1&sparkline=false&locale=en")
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

    private fun parseApiResponse(apiResponse: String): List<Image> {
        val jsonArray = JSONArray(apiResponse)

        val dataList = mutableListOf<Image>()

        for (i in 0 until jsonArray.length()) {
            val cryptoObject = jsonArray.getJSONObject(i)

            val id = cryptoObject.getString("id")
            val image = cryptoObject.getString("image")

            dataList.add(Image(id, image))
        }

        return dataList
    }
}