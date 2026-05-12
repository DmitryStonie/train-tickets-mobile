package com.yurin.train_tickets_mobile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yurin.train_tickets_mobile.domain.entity.Status
import com.yurin.train_tickets_mobile.domain.usecase.BookSeatUseCase
import com.yurin.train_tickets_mobile.domain.usecase.GetSeatsUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = SeatsViewModel.SeatsViewModelFactory::class)
class SeatsViewModel @AssistedInject constructor(
    private val getSeatsUseCase: GetSeatsUseCase,
    private val bookSeatUseCase: BookSeatUseCase,
    @Assisted private val tripId: Int,
) : ViewModel() {

    private val _screenState = MutableStateFlow<SeatsScreenState>(SeatsScreenState.Initial)
    val screenState = _screenState.asStateFlow()

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _screenState.value = SeatsScreenState.Error(throwable.message ?: "Что-то пошло не так")
    }

    private val coroutineExceptionHandler2 = CoroutineExceptionHandler { _, throwable ->
        if (_screenState.value is SeatsScreenState.Content) {
            _screenState.update { currentState ->
                (currentState as SeatsScreenState.Content).copy(
                    bookingState = BookingState.Error(throwable.message ?: "Что-то пошло не так")
                )
            }
        }
    }

    fun getSeats() {
        _screenState.value = SeatsScreenState.Loading
        viewModelScope.launch(coroutineExceptionHandler) {
            val seats = getSeatsUseCase(tripId).filter {
                it.seats.any { seat -> seat.status == Status.AVAILABLE }
            }
            _screenState.value = SeatsScreenState.Content(seats, BookingState.Initial)
        }
    }

    fun bookSeat(seatId: Int, onBookSuccess: (Int) -> Unit) {
        if (_screenState.value is SeatsScreenState.Content) {
            _screenState.update { currentState ->
                (currentState as SeatsScreenState.Content).copy(
                    bookingState = BookingState.Loading
                )
            }
        }
        viewModelScope.launch(coroutineExceptionHandler2) {
            val booking = bookSeatUseCase(seatId)
            if (_screenState.value is SeatsScreenState.Content) {
                _screenState.update { currentState ->
                    (currentState as SeatsScreenState.Content).copy(
                        bookingState = BookingState.Success(booking)
                    )
                }
                onBookSuccess(booking.id)
            }
        }
    }

    fun setBookingInitialState(){
        if (_screenState.value is SeatsScreenState.Content) {
            _screenState.update { currentState ->
                (currentState as SeatsScreenState.Content).copy(
                    bookingState = BookingState.Initial
                )
            }
        }
    }

    @AssistedFactory
    interface SeatsViewModelFactory {
        fun create(tripId: Int): SeatsViewModel
    }
}