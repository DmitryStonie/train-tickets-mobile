package com.yurin.train_tickets_mobile.presentation

import com.yurin.train_tickets_mobile.domain.entity.Trip

sealed interface SearchScreenState {
    data object Initial: SearchScreenState
    data class Error(val message: String): SearchScreenState
    data class Content(val departure: List<String>, val destination: List<String>, val tripListState: TripListState): SearchScreenState
}

sealed interface TripListState {
    data object NoTrips: TripListState
    data object Loading: TripListState
    data class Error(val message: String): TripListState
    data class Content(val trips: List<Trip>): TripListState
}