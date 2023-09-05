import android.content.Context
import android.content.SharedPreferences
import com.example.cryptowithfragments.data.datasource.coin.CoinDataSourceInterface
import com.example.cryptowithfragments.data.datasource.db.CoinDao
import com.example.cryptowithfragments.domain.entity.Coin
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

import java.lang.reflect.Type

class CoinLocalDataSource(context: Context) : CoinDataSourceInterface {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MySharedPreferences", Context.MODE_PRIVATE)
    private val sharedPrefsKey = "favoriteCoins"
    private val gson = Gson()
    override suspend fun getCoins(): List<Coin> {
        TODO("Not yet implemented")
    }

    override suspend fun saveCoins(coins: List<Coin>) {
        TODO("Not yet implemented")
    }

    override suspend fun saveFavorite(coin: Coin) {
        val favorites = loadFavorites().toMutableList()
        favorites.add(coin)
        val favoritesJson = gson.toJson(favorites)
        sharedPreferences.edit().putString(sharedPrefsKey, favoritesJson).apply()
    }

    override suspend fun loadFavorites(): List<Coin> {
        val favoritesJson = sharedPreferences.getString(sharedPrefsKey, null)
        val type: Type = object : TypeToken<List<Coin>>() {}.type

        return if (!favoritesJson.isNullOrEmpty()) {
            gson.fromJson(favoritesJson, type)
        } else {
            emptyList()
        }
    }

    override suspend fun deleteFavorite(coin: Coin) {
        val favorites = loadFavorites().toMutableList()
        favorites.removeAll { it.id == coin.id }
        val favoritesJson = gson.toJson(favorites)
        sharedPreferences.edit().putString(sharedPrefsKey, favoritesJson).apply()
    }

}
