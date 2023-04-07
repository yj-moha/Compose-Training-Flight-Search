package com.example.flightsearch.data.repositories.impl

import com.example.flightsearch.data.daos.AirportDao
import com.example.flightsearch.data.daos.FavoriteDao
import com.example.flightsearch.data.models.Airport
import com.example.flightsearch.data.models.Route
import com.example.flightsearch.data.repositories.FlightSearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class OfflineFlightSearchRepository(
    private val airportDao: AirportDao,
    private val favoriteDao: FavoriteDao,
) : FlightSearchRepository{
    override fun getAirportStream(iataCode: String): Flow<Airport> = airportDao.getAirport(iataCode)

    override fun getAllAirportsStream(): Flow<List<Airport>> = airportDao.getAllAirports()

    override fun findAirportsStream(word: String): Flow<List<Airport>> {
        var newWord = ""
        word.forEach { char -> newWord += "%$char" }
        return if (newWord.isBlank()) flowOf(listOf()) else airportDao.findAirports("$newWord%")
    }

    override suspend fun insertFavoriteRoute(route: Route) = favoriteDao.insert(route)

    override suspend fun deleteFavoriteRoute(route: Route) = favoriteDao.delete(route)

    override fun isFavoriteRoute(departureCode: String, destinationCode: String): Flow<Boolean> =
        favoriteDao.countFavoriteRoutes(departureCode, destinationCode).map { count -> count > 0 }

    override fun getAllFavoriteRoutesStream(): Flow<List<Route>> = favoriteDao.getAllFavoriteRoutes()

    override fun findFavoriteRoutesStream(departureCode: String): Flow<List<Route>> =
        favoriteDao.findFavoriteRoutes(departureCode)
}