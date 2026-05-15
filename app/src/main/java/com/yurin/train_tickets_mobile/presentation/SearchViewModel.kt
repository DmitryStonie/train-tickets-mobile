package com.yurin.train_tickets_mobile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yurin.train_tickets_mobile.domain.usecase.GetDepartureUseCase
import com.yurin.train_tickets_mobile.domain.usecase.GetDestinationsUseCase
import com.yurin.train_tickets_mobile.domain.usecase.GetTripsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getDestinationsUseCase: GetDestinationsUseCase,
    private val getDepartureUseCase: GetDepartureUseCase,
    private val getTripsUseCase: GetTripsUseCase,
) : ViewModel() {

    private val _screenState = MutableStateFlow<SearchScreenState>(SearchScreenState.Initial)
    val screenState = _screenState.asStateFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _screenState.value = SearchScreenState.Error(throwable.message ?: "Что-то пошло не так")
    }

    private val coroutineExceptionHandler2 = CoroutineExceptionHandler { _, throwable ->
        if (_screenState.value is SearchScreenState.Content) {
            _screenState.update { currentState ->
                (currentState as SearchScreenState.Content).copy(
                    tripListState = TripListState.Error(throwable.message ?: "Что-то пошло не так")
                )
            }
        }
    }

    fun getDestAndDep() {
        viewModelScope.launch(coroutineExceptionHandler) {
            val dest = getDestinationsUseCase()
            val dep = getDepartureUseCase()
            _screenState.value = SearchScreenState.Content(dep, dest, TripListState.NoTrips)
        }
    }

    fun getTrips(departure: String, destination: String, departureDate: Long?) {
        if (_screenState.value is SearchScreenState.Content) {
            _screenState.update { currentState ->
                (currentState as SearchScreenState.Content).copy(
                    tripListState = TripListState.Loading
                )
            }
        }
        viewModelScope.launch(coroutineExceptionHandler2) {
            val trips = getTripsUseCase(departure, destination, departureDate)
            if (_screenState.value is SearchScreenState.Content) {
                _screenState.update { currentState ->
                    (currentState as SearchScreenState.Content).copy(
                        tripListState = if (trips.any { it.availableSeatsNumber > 0 }) TripListState.Content(
                            trips
                        ) else TripListState.NoTrips
                    )
                }
            }
        }
    }
}