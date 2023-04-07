/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.flightsearch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flightsearch.FlightSearchApplication
import com.example.flightsearch.data.models.Airport
import com.example.flightsearch.data.repositories.FlightSearchRepository
import com.example.flightsearch.data.repositories.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AppViewModel(private val userPreferencesRepository: UserPreferencesRepository, private val flightSearchRepository: FlightSearchRepository) : ViewModel() {
    private val _searchUiState = MutableStateFlow(SearchUiState())
    val searchUiState: StateFlow<SearchUiState> =
        userPreferencesRepository.searchString.map { searchString ->
            SearchUiState(searchString)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(1_000),
            initialValue = SearchUiState()
        )

    var airportsUiState: StateFlow<List<Airport>> =
        flightSearchRepository.findAirportsStream(searchUiState.value.searchString)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = listOf()
            )

    var airportUiState: StateFlow<Airport?> = MutableStateFlow(null)

    val allAirportsUiState: StateFlow<List<Airport>> =
        flightSearchRepository.getAllAirportsStream()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = listOf()
            )

    fun setSearchString(searchString: String) {
        _searchUiState.value = SearchUiState(searchString)
        viewModelScope.launch {
            userPreferencesRepository.saveSearchStringPreference(searchString)
        }

        airportsUiState = flightSearchRepository.findAirportsStream(_searchUiState.value.searchString)
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000L),
                initialValue = listOf()
            )
    }

    fun setAirport(airport: Airport?) {
        airportUiState = MutableStateFlow(airport)
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as FlightSearchApplication)
                AppViewModel(application.userPreferencesRepository, application.container.flightSearchRepository)
            }
        }
    }
}

data class SearchUiState(val searchString: String = "")