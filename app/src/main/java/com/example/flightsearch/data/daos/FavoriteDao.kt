package com.example.flightsearch.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.flightsearch.data.models.Route
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(route: Route)

    @Delete
    suspend fun delete(route: Route)

    @Query("SELECT COUNT(id) FROM favorite WHERE departure_code = :departureCode AND destination_code = :destinationCode")
    fun countFavoriteRoutes(departureCode: String, destinationCode: String): Flow<Int>

    @Query("SELECT * FROM favorite")
    fun getAllFavoriteRoutes(): Flow<List<Route>>

    @Query("SELECT * FROM favorite WHERE departure_code = :departureCode")
    fun findFavoriteRoutes(departureCode: String): Flow<List<Route>>
}