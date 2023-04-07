package com.example.flightsearch.data.daos

import androidx.room.Dao
import androidx.room.Query
import com.example.flightsearch.data.models.Airport
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDao {
    @Query("SELECT * FROM airport WHERE iata_code = :iataCode")
    fun getAirport(iataCode: String): Flow<Airport>

    @Query("SELECT * FROM airport ORDER BY passengers DESC")
    fun getAllAirports(): Flow<List<Airport>>

    @Query("SELECT * FROM airport WHERE UPPER(iata_code) LIKE UPPER(:word) OR UPPER(name) LIKE UPPER(:word) ORDER BY passengers DESC")
    fun findAirports(word: String): Flow<List<Airport>>
}