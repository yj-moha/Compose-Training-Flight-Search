package com.example.flightsearch.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.example.flightsearch.data.models.Airport

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AirportList(airportsUiState: List<Airport>, onClick: (airport: Airport) -> Unit) {
    LazyColumn {
        items(airportsUiState) { airport ->
            ListItem(
                headlineText = { TextButton(onClick = { onClick(airport) }, content = { Text(airport.iataCode) }) },
                supportingText = { TextButton(onClick = { onClick(airport) }, content = { Text(airport.name) }) },
            )
        }
    }
}