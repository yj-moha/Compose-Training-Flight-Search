package com.example.flightsearch.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.flightsearch.data.daos.AirportDao
import com.example.flightsearch.data.daos.FavoriteDao
import com.example.flightsearch.data.models.Airport
import com.example.flightsearch.data.models.Route

@Database(entities = [Airport::class, Route::class], version = 1, exportSchema = false)
abstract class FlightSearchDatabase : RoomDatabase() {
    abstract fun airportsDao(): AirportDao
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var Instance: FlightSearchDatabase? = null
        fun getDatabase(
            context: Context
        ): FlightSearchDatabase {
            return Instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    FlightSearchDatabase::class.java,
                    "flight_search_database"
                )
                    .createFromAsset("database/flight_search.db")
                    .build()
                Instance = instance

                instance
            }
        }
    }
}