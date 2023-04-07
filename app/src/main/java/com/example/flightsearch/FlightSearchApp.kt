package com.example.flightsearch

import androidx.compose.foundation.layout.Column
import androidx.compose.material.TextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.data.models.Airport
import com.example.flightsearch.ui.AirportList
import com.example.flightsearch.ui.AppViewModel
import com.example.flightsearch.ui.RouteList
import com.example.flightsearch.ui.SearchUiState

@Composable
fun FlightSearchApp(
    modifier: Modifier = Modifier,
    appViewModel: AppViewModel = viewModel(factory = AppViewModel.Factory)
) {
    FlightSearchApp(
        searchUiState = appViewModel.searchUiState.collectAsState().value,
        airportUiState = appViewModel.airportUiState.collectAsState().value,
        airportsUiState = appViewModel.airportsUiState.collectAsState().value,
        allAirportsUiState = appViewModel.allAirportsUiState.collectAsState().value,
        setSearchString = appViewModel::setSearchString,
        setAirport = appViewModel::setAirport,
        modifier = modifier
    )
}

@Composable
private fun FlightSearchApp(
    searchUiState: SearchUiState,
    airportUiState: Airport?,
    airportsUiState: List<Airport>,
    allAirportsUiState: List<Airport>,
    setSearchString: (String) -> Unit,
    setAirport: (Airport?) -> Unit,
    modifier: Modifier = Modifier
) {
    Column() {
        TextField(
            value = searchUiState.searchString,
            onValueChange = { searchString ->
                setSearchString(searchString)
                if (airportUiState != null) setAirport(null)
            },
            label = { Text("Search") }
        )

        if (airportUiState == null && airportsUiState.isNotEmpty()) {
            AirportList(
                airportsUiState = airportsUiState
            ) { airport: Airport ->
                setSearchString(airport.iataCode)
                setAirport(airport)
            }
        } else if (airportUiState != null) {
            RouteList(airportUiDtate = airportUiState, allAirportsUiState = allAirportsUiState)
        }
    }
}

@Preview
@Composable
fun DessertReleaseAppPreview() {
    FlightSearchApp(
        searchUiState = SearchUiState(),
        airportUiState = null,
        airportsUiState = listOf(),
        allAirportsUiState = listOf(),
        setSearchString = {},
        setAirport = {}
    )
}