package com.example.flightsearch.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.example.flightsearch.data.models.Airport

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RouteList(airportUiDtate: Airport, allAirportsUiState: List<Airport>) {
    LazyColumn {
        items(allAirportsUiState) { airport: Airport ->
            if (airport.id != airportUiDtate.id) {
                ListItem(
                    headlineText = { Text("DEPART") },
                    supportingText = { Row {
                        Text(airportUiDtate.iataCode)
                        Text(airportUiDtate.name)
                    } },
                )
                ListItem(
                    headlineText = { Text("ARRIVE") },
                    supportingText = { Row {
                        Text(airport.iataCode)
                        Text(airport.name)
                    } },
                )
            }
        }
    }
}