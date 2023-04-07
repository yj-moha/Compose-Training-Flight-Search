package com.example.flightsearch.data.repositories

import com.example.flightsearch.data.models.Airport
import com.example.flightsearch.data.models.Route
import kotlinx.coroutines.flow.Flow

interface FlightSearchRepository {
    fun getAirportStream(iataCode: String): Flow<Airport>

    fun getAllAirportsStream(): Flow<List<Airport>>

    fun findAirportsStream(word: String): Flow<List<Airport>>

    suspend fun insertFavoriteRoute(route: Route)

    suspend fun deleteFavoriteRoute(route: Route)

    fun isFavoriteRoute(departureCode: String, destinationCode: String): Flow<Boolean>

    fun getAllFavoriteRoutesStream(): Flow<List<Route>>

    fun findFavoriteRoutesStream(departureCode: String): Flow<List<Route>>
}