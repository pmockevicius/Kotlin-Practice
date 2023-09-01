package com.example.cryptowithfragments.data.datasource.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cryptowithfragments.domain.entity.Coin

@Database(entities = [Coin::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun coinDao(): CoinDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "Crypto-app"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}